package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.user.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameStateRepository gameStateRepository;
    private final CardRepository cardRepository;
    private final PlayerRepository playerRepository;

    @Override
    public GameState startGame(List<User> challenged) {
        Random r = new Random();
        List<Player> players = challenged.stream()
                .map(user -> new Player(user))
                .peek(player -> playerRepository.save(player))
                .collect(Collectors.toList());
        GameState gameState = new GameState();
        gameState.setPlayers(players.stream()
                .map(Player::getId)
                .collect(Collectors.toList()));
        List<Card> cardList = cardRepository.findAll();
        ArrayList<Card> cardsOnTable = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            int finalI = i;
            List<Card> cardsFromLevelList = cardList.stream()
                    .filter(card -> card.getLevel() == finalI)
                    .collect(Collectors.toList());
            for (int j = 0; j < 4; j++) {
                Card card = cardsFromLevelList.get(r.nextInt(cardsFromLevelList.size()));
                cardList.remove(card);
                cardsFromLevelList.remove(card);
                cardsOnTable.add(card);
            }
        }
        gameState.setCards(cardList);
        gameState.setCardsOnTable(cardsOnTable);
        int startTokens = players.size() == 4 ? 7 : players.size() + 2;
        gameState.setDiamonds(startTokens);
        gameState.setEmeralds(startTokens);
        gameState.setRubys(startTokens);
        gameState.setSaphires(startTokens);
        gameState.setOnyxs(startTokens);
        gameState.setLastPlayerName("init");
        gameState = gameStateRepository.save(gameState);
        return gameState;
    }

    @Override
    public GameState checkPossibleActions(GameState gameState, Player player) {
        Map<String, Integer> tokensFromCards = player.getMapOfCards();
        Map<String, Integer> tokensMap = player.getTokensMap();
        Set<String> keySet = tokensMap.keySet();
        for (String key :
                keySet) {
            if (tokensFromCards.get(key) != null) {
                tokensMap.put(key, tokensMap.get(key) + tokensFromCards.get(key));
            }
        }
        gameState.setCardsOnTable(gameState.getCardsOnTable().stream()
                .map(card -> {
                    if (card.getDiamondCost() <= tokensMap.get("diamond") &&
                            card.getEmeraldCost() <= tokensMap.get("emerald") &&
                            card.getRubyCost() <= tokensMap.get("ruby") &&
                            card.getSaphireCost() <= tokensMap.get("saphire") &&
                            card.getOnyxCost() <= tokensMap.get("onyx")) {
                        card.setClickable(true);
                    }
                    return card;
                })
                .collect(Collectors.toList()));

        return gameState;
    }

    @Override
    public GameStateWrapper getFullStateAtInit(CurrentUser currentUser) {
        GameState gameState = gameStateRepository.findById(currentUser.getUser().getGameState().getId()).get();
        GameStateWrapper gameStateWrapper = new GameStateWrapper();
        boolean isItMyTurn = false;
        if ("init".equals(gameState.getLastPlayerName())) {
            isItMyTurn = currentUser.getUsername().equals(playerRepository.findById(gameState.getPlayers().get(0)).get().getUser().getUsername());
        } else {
            isItMyTurn = currentUser.getUsername().equals(getNextPlayer(gameState));
        }
        if (isItMyTurn) {
            gameState = checkPossibleActions(gameState, playerRepository.findFirstByUser(currentUser.getUser()).get());
        }
        gameStateWrapper.setCardsOnTable(gameState.getCardsOnTable());
        gameStateWrapper.setPlayers(gameState
                .getPlayers()
                .stream()
                .map(aLong -> playerRepository.findById(aLong).get())
                .map(Player::toString)
                .collect(Collectors.toList())
        );
        //to jest bardzo jednak nieeleganckie że tokeny są tak przetrzymywane
        ArrayList<Integer> tokens = new ArrayList<>();
        tokens.add(gameState.getDiamonds());
        tokens.add(gameState.getEmeralds());
        tokens.add(gameState.getRubys());
        tokens.add(gameState.getSaphires());
        tokens.add(gameState.getOnyxs());
        gameStateWrapper.setTokens(tokens);
        gameStateWrapper.setIsItMyTurn(isItMyTurn);
        return gameStateWrapper;
    }

    @Override
    public boolean checkTokenGain(String token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        String currentPlayer = getNextPlayer(gameState);
        if (currentUser.getUsername().equals(currentPlayer)) {
            if (checkTokenWithGetter(token, gameState, 3)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkTokenWithGetter(String token, GameState gameState, int i) {
        return (("d".equals(token) && gameState.getDiamonds() > i) ||
                ("e".equals(token) && gameState.getEmeralds() > i) ||
                ("r".equals(token) && gameState.getRubys() > i) ||
                ("s".equals(token) && gameState.getSaphires() > i) ||
                ("o".equals(token) && gameState.getOnyxs() > i));
    }

    @Override
    public boolean checkTokenGain(Map<String, Object> token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        Set<String> keySet = token.keySet();
        Set<String> set = keySet.stream()
                .map(s -> (String) token.get(s))
                .filter(s -> checkTokenWithGetter(s, gameState, 0))
                .collect(Collectors.toSet());
        return set.size() == 3;
    }

    @Override
    public void addTokens(String token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        //W związku z tym trzeba pamiętać o kasowaniu playerów
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();
        if ("d".equals(token)) {
            gameState.setDiamonds(gameState.getDiamonds() - 2);
            player.setDiamonds(player.getDiamonds() + 2);
        } else if ("e".equals(token)) {
            gameState.setEmeralds(gameState.getEmeralds() - 2);
            player.setEmeralds(player.getEmeralds() + 2);
        } else if ("r".equals(token)) {
            gameState.setRubys(gameState.getRubys() - 2);
            player.setRubys(player.getRubys() + 2);
        } else if ("s".equals(token)) {
            gameState.setSaphires(gameState.getSaphires() - 2);
            player.setSaphires(player.getSaphires() + 2);
        } else if ("o".equals(token)) {
            gameState.setOnyxs(gameState.getOnyxs() - 2);
            player.setOnyxs(player.getOnyxs() + 2);
        }
        gameState.setLastPlayerName(currentUser.getUsername());
        playerRepository.save(player);
        gameStateRepository.save(gameState);
    }

    @Override
    public void addTokens(Map<String, Object> token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        //W związku z tym trzeba pamiętać o kasowaniu playerów
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();
        token.keySet().stream()
                .map(s -> (String) token.get(s))
                .forEach(s -> {
                    if ("d".equals(s)) {
                        gameState.setDiamonds(gameState.getDiamonds() - 1);
                        player.setDiamonds(player.getDiamonds() + 1);
                    } else if ("e".equals(s)) {
                        gameState.setEmeralds(gameState.getEmeralds() - 1);
                        player.setEmeralds(player.getEmeralds() + 1);
                    } else if ("r".equals(s)) {
                        gameState.setRubys(gameState.getRubys() - 1);
                        player.setRubys(player.getRubys() + 1);
                    } else if ("s".equals(s)) {
                        gameState.setSaphires(gameState.getSaphires() - 1);
                        player.setSaphires(player.getSaphires() + 1);
                    } else if ("o".equals(s)) {
                        gameState.setOnyxs(gameState.getOnyxs() - 1);
                        player.setOnyxs(player.getOnyxs() + 1);
                    }
                });
        gameState.setLastPlayerName(currentUser.getUsername());
        playerRepository.save(player);
        gameStateRepository.save(gameState);
    }

    @Override
    public boolean checkBuyCard(String cardId, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        String currentPlayer = getNextPlayer(gameState);
        if (currentUser.getUsername().equals(currentPlayer)) {
            GameState gameState1 = checkPossibleActions(gameState,
                    playerRepository.findFirstByUser(currentUser.getUser()).get());
            if (gameState1.getCardsOnTable().stream()
                    .filter(card -> card.getId().equals(Long.parseLong(cardId)))
                    .map(card -> card.getClickable())
                    .findFirst().get()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void buyCard(String cardId, CurrentUser currentUser) {
        Random r = new Random();
        GameState gameState = currentUser.getUser().getGameState();
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();
        Card card = cardRepository.findById(Long.parseLong(cardId)).get();
        Map<String, Integer> payment = player.addCard(card);
        gameState.addPayment(payment);
        List<Card> cardsOnTable = gameState.getCardsOnTable();
        int cardToChange = cardsOnTable.indexOf(card);
        List<Card> cardList = gameState.getCards();
        List<Card> cardsFromLevelList = cardList.stream()
                .filter(c -> c.getLevel() == card.getLevel())
                .collect(Collectors.toList());
        Card newCard = cardsFromLevelList.get(r.nextInt(cardsFromLevelList.size()));
        cardList.remove(newCard);
        gameState.setCards(cardList);
        cardsOnTable.set(cardToChange, newCard);
        gameState.setCardsOnTable(cardsOnTable);
        gameState.setLastPlayerName(currentUser.getUsername());
        playerRepository.save(player);
        gameStateRepository.save(gameState);
    }

    private String getNextPlayer(GameState gameState) {
        String lastPlayerName = gameState.getLastPlayerName();
        List<Player> players = gameState.getPlayers().stream()
                .map(aLong -> playerRepository.findById(aLong).get())
                .collect(Collectors.toList());
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUser().getUsername().equals(lastPlayerName)) {
                try {
                    return players.get(i + 1).getUser().getUsername();
                } catch (Exception e) {
                    return players.get(0).getUser().getUsername();
                }
            }
        }
        if ("init".equals(lastPlayerName)) {
            return players.get(0).getUser().getUsername();
        }
        return null;
    }
}
