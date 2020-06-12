package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.user.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
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

        HashMap<TokenType, Integer> tokensOnTable = new HashMap<>();
        Arrays.stream(TokenType.values()).forEach(tokenType -> tokensOnTable.put(tokenType, startTokens));
        gameState.setTokensOnTable(tokensOnTable);
        gameState.setLastPlayerName("init");
        gameState = gameStateRepository.save(gameState);
        return gameState;
    }

    @Override
    public GameState checkPossibleActions(GameState gameState, Player player) {
        Map<TokenType, Integer> tokensFromCards = player.getMapOfCards();
        Map<TokenType, Integer> tokensMap = player.getPlayerTokens();
        HashMap<TokenType, Integer> localTokenMap = new HashMap<>();
        Arrays.stream(TokenType.values()).forEach(tokenType ->
                localTokenMap.put(tokenType, tokensMap.get(tokenType) + tokensFromCards.get(tokenType))
        );
        gameState.setCardsOnTable(gameState.getCardsOnTable().stream()
                .map(card -> {
                    if (card.getCost().keySet().stream()
                            .allMatch(tokenType -> card.getCost().get(tokenType) <= (localTokenMap.get(tokenType) == null ? 0 : localTokenMap.get(tokenType)))) {
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
        gameStateWrapper.setTokens(gameState.getTokensOnTable());
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
        return gameState.getTokensOnTable().get(TokenType.valueOf(token)) > i;
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
        TokenType tokenType = TokenType.valueOf(token);
        //W związku z tym trzeba pamiętać o kasowaniu playerów
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();
        Map<TokenType, Integer> playerTokens = player.getPlayerTokens();
        playerTokens.put(tokenType, (playerTokens.get(tokenType) == null ? 0 : playerTokens.get(tokenType)) + 2);
        player.setPlayerTokens(playerTokens);
        Map<TokenType, Integer> tokensOnTable = gameState.getTokensOnTable();
        tokensOnTable.put(tokenType, (tokensOnTable.get(tokenType) == null ? 0 : tokensOnTable.get(tokenType)) - 2);
        gameState.setTokensOnTable(tokensOnTable);
        gameState.setLastPlayerName(currentUser.getUsername());
        playerRepository.save(player);
        gameStateRepository.save(gameState);
    }

    @Override
    public void addTokens(Map<String, Object> token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        //W związku z tym trzeba pamiętać o kasowaniu playerów
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();
        Map<TokenType, Integer> playerTokens = player.getPlayerTokens();
        Map<TokenType, Integer> tokensOnTable = gameState.getTokensOnTable();
        token.keySet().stream()
                .map(s -> (String) token.get(s))
                .forEach(s -> {
                    TokenType tokenType = TokenType.valueOf(s);
                    playerTokens.put(tokenType, (playerTokens.get(tokenType) == null ? 0 : playerTokens.get(tokenType)) + 1);
                    tokensOnTable.put(tokenType, (tokensOnTable.get(tokenType) == null ? 0 : tokensOnTable.get(tokenType)) - 1);
                });
        player.setPlayerTokens(playerTokens);
        gameState.setTokensOnTable(tokensOnTable);
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
        Map<TokenType, Integer> payment = player.addCard(card);
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

    @Override
    public boolean checkMixedTokenGain(Map<String, Object> token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        Set<String> keySet = token.keySet();
        Set<String> set = keySet.stream()
                .map(s -> (String) token.get(s))
                .filter(s -> checkTokenWithGetter(s, gameState, 0))
                .collect(Collectors.toSet());
        int numberOfEmptyTokens = Arrays.stream(TokenType.values())
                .filter(tokenType -> gameState.getTokensOnTable().get(tokenType) == 0)
                .collect(Collectors.toList())
                .size();
        return set.size() == (5 - numberOfEmptyTokens);
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
