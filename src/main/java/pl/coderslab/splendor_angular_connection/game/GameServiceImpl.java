package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.user.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class GameServiceImpl implements GameService {
    private final GameStateRepository gameStateRepository;
    private final CardRepository cardRepository;
    private final PlayerRepository playerRepository;
    private final NobleRepository nobleRepository;

    @Override
    public GameState startGame(List<User> challenged) {
        Random r = new Random();
        List<Player> players = challenged.stream()
                .map(Player::new)
                .peek(playerRepository::save)
                .collect(Collectors.toList());
        GameState gameState = new GameState();
        gameState.setPlayers(players);
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
        tokensOnTable.put(TokenType.GOLD, 5);
        gameState.setTokensOnTable(tokensOnTable);

        int noblesNum = players.size() + 1;
        List<Noble> nobles = nobleRepository.findAll();
        ArrayList<Noble> noblesOnTable = new ArrayList<>();
        for (int i = 0; i < noblesNum; i++) {
            Noble noble = nobles.get(r.nextInt(nobles.size()));
            nobles.remove(noble);
            noblesOnTable.add(noble);
        }
        gameState.setNobles(noblesOnTable);
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
                    if (card
                            .getCost()
                            .keySet()
                            .stream()
                            .map(tokenType -> Math.max(0,
                                    card
                                            .getCost()
                                            .get(tokenType) - (localTokenMap.get(tokenType) == null ? 0 : localTokenMap.get(tokenType))))
                            .reduce(0, Integer::sum) <= localTokenMap.get(TokenType.GOLD)) {
                        card.setClickable(true);
                    }
                    return card;
                })
                .collect(Collectors.toList()));

        player.setCardsInHand(player.getCardsInHand().stream()
                .map(card -> {
                    if (card
                            .getCost()
                            .keySet()
                            .stream()
                            .map(tokenType -> Math.max(0,
                                    card
                                            .getCost()
                                            .get(tokenType) - (localTokenMap.get(tokenType) == null ? 0 : localTokenMap.get(tokenType))))
                            .reduce(0, Integer::sum) <= localTokenMap.get(TokenType.GOLD)) {
                        card.setClickable(true);
                    }
                    return card;
                })
                .collect(Collectors.toList()));
        gameState.setPlayers(gameState.getPlayers()
                .stream()
                .map(player1 -> {
                    return Objects.equals(player1.getId(), player.getId()) ? player : player1;
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
            isItMyTurn = currentUser.getUsername().equals(gameState.getPlayers().get(0).getUser().getUsername());
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
                .map(PlayerWrapper::new)
                .collect(Collectors.toList())
        );
        gameStateWrapper.setTokens(gameState.getTokensOnTable());
        gameStateWrapper.setNobles(gameState.getNobles());
        gameStateWrapper.setIsItMyTurn(isItMyTurn);
        gameStateWrapper.setIsItReserveTime(false);
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
    public String addTokens(String token, CurrentUser currentUser) {
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
        int i = howManyTokensNeedToBeGivenBack(player);
        if (i == 0) {
            boolean isDialogNeeded = giveNobleToPlayer(player, gameState);
            return "Operation Confirmed";
        } else {
            playerRepository.save(player);
            gameStateRepository.save(gameState);
            return "Give back tokens";
        }
    }

    private boolean giveNobleToPlayer(Player player, GameState gameState) {
        Map<TokenType, Integer> cards = player.getMapOfCards();
        List<Noble> nobles = gameState.getNobles();
        List<Noble> availableNobles = nobles.stream()
                .filter(noble -> Arrays.stream(TokenType.values())
                        .allMatch(tokenType -> cards.get(tokenType) >=
                                (noble.getCardCombination().get(tokenType) != null ? noble.getCardCombination().get(tokenType) : 0)))
                .collect(Collectors.toList());
        if (availableNobles.size() == 1) {
            Noble noble = availableNobles.get(0);
            nobles.remove(noble);
            player.addNoble(noble);
            gameState.setNobles(nobles);
            gameState.setLastPlayerName(player.getUser().getUsername());
            playerRepository.save(player);
            gameStateRepository.save(gameState);
            return false;
        } else if (availableNobles.size() > 1) {
            return true;
        }
        gameState.setLastPlayerName(player.getUser().getUsername());
        playerRepository.save(player);
        gameStateRepository.save(gameState);
        return false;
    }

    @Override
    public String addTokens(Map<String, Object> token, CurrentUser currentUser) {
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
        int i = howManyTokensNeedToBeGivenBack(player);
        if (i == 0) {
            boolean isDialogNeeded = giveNobleToPlayer(player, gameState);
            return "Operation Confirmed";
        } else {
            playerRepository.save(player);
            gameStateRepository.save(gameState);
            return "Give back tokens";
        }
    }

    @Override
    public boolean checkBuyCard(String cardId, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        String currentPlayer = getNextPlayer(gameState);
        if (currentUser.getUsername().equals(currentPlayer)) {
            GameState gameState1 = checkPossibleActions(gameState,
                    playerRepository.findFirstByUser(currentUser.getUser()).get());
            return gameState1.getCardsOnTable().stream()
                    .filter(card -> card.getId().equals(Long.parseLong(cardId)))
                    .anyMatch(Card::getClickable) || gameState1.getPlayers()
                    .stream()
                    .filter(player -> currentPlayer.equals(player.getUser().getUsername()))
                    .findFirst().get().getCardsInHand().stream()
                    .filter(card -> card.getId().equals(Long.parseLong(cardId)))
                    .anyMatch(Card::getClickable);
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
        if (cardToChange != -1) {
            List<Card> cardList = gameState.getCards();
            List<Card> cardsFromLevelList = cardList.stream()
                    .filter(c -> Objects.equals(c.getLevel(), card.getLevel()))
                    .collect(Collectors.toList());
            Card newCard = cardsFromLevelList.get(r.nextInt(cardsFromLevelList.size()));
            cardList.remove(newCard);
            gameState.setCards(cardList);
            cardsOnTable.set(cardToChange, newCard);
            gameState.setCardsOnTable(cardsOnTable);
        } else {
            List<Card> cardsInHand = player.getCardsInHand();
            cardsInHand.remove(card);
            player.setCardsInHand(cardsInHand);
        }
        boolean isDialogNeeded = giveNobleToPlayer(player, gameState);
//        return "Operation Confirmed";
    }

    @Override
    public boolean checkMixedTokenGain(Map<String, Object> token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        Set<String> keySet = token.keySet();
        Set<String> set = keySet.stream()
                .map(s -> (String) token.get(s))
                .filter(s -> checkTokenWithGetter(s, gameState, 0))
                .collect(Collectors.toSet());
        int numberOfEmptyTokens = (int) Arrays.stream(TokenType.values())
                .filter(tokenType -> gameState.getTokensOnTable().get(tokenType) == 0).count();
        return set.size() == (5 - numberOfEmptyTokens);
    }

    @Override
    public int howManyTokensNeedToBeGivenBack(Player player) {
        Integer totalTokens = Arrays.stream(TokenType.values())
                .map(tokenType -> player.getPlayerTokens().get(tokenType))
                .reduce(0, Integer::sum);
        return Math.max(0, totalTokens - 10);
    }

    @Override
    public boolean checkTokensReturn(List<TokenType> tokens, CurrentUser currentUser) {
        Map<TokenType, Integer> playerTokens = playerRepository.
                findFirstByUser(currentUser.getUser())
                .get().getPlayerTokens();
        return tokens.stream().allMatch(tokenType -> playerTokens.get(tokenType) - 1 >= 0);
    }

    @Override
    public String returnTokens(List<TokenType> tokens, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        //W związku z tym trzeba pamiętać o kasowaniu playerów
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();
        Map<TokenType, Integer> playerTokens = player.getPlayerTokens();
        Map<TokenType, Integer> tokensOnTable = gameState.getTokensOnTable();
        tokens
                .forEach(tokenType -> {
                    playerTokens.put(tokenType, (playerTokens.get(tokenType) == null ? 0 : playerTokens.get(tokenType)) - 1);
                    tokensOnTable.put(tokenType, (tokensOnTable.get(tokenType) == null ? 0 : tokensOnTable.get(tokenType)) + 1);
                });
        player.setPlayerTokens(playerTokens);
        gameState.setTokensOnTable(tokensOnTable);
        int i = howManyTokensNeedToBeGivenBack(player);
        if (i == 0) {
            boolean isDialogNeeded = giveNobleToPlayer(player, gameState);
            return "Operation Confirmed";
        } else {
            playerRepository.save(player);
            gameStateRepository.save(gameState);
            return "Give back tokens";
        }
    }

    @Override
    public boolean checkGoldToken(CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        return gameState.getTokensOnTable().get(TokenType.GOLD) > 0;
    }

    @Override
    public GameStateWrapper addGoldToken(CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        //W związku z tym trzeba pamiętać o kasowaniu playerów
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();//to trzeba jakoś powiązać z obiektem gameState
        Map<TokenType, Integer> playerTokens = player.getPlayerTokens();
        Map<TokenType, Integer> tokensOnTable = gameState.getTokensOnTable();
        playerTokens.put(TokenType.GOLD, (playerTokens.get(TokenType.GOLD) == null ? 0 : playerTokens.get(TokenType.GOLD)) + 1);
        tokensOnTable.put(TokenType.GOLD, (tokensOnTable.get(TokenType.GOLD) == null ? 0 : tokensOnTable.get(TokenType.GOLD)) - 1);
        player.setPlayerTokens(playerTokens);
        gameState.setTokensOnTable(tokensOnTable);
//        int i = howManyTokensNeedToBeGivenBack(player);
        gameState
                .setCardsOnTable(
                        gameState
                                .getCardsOnTable()
                                .stream()
                                .map(card -> {
                                    card.setClickable(true);
                                    return card;
                                })
                                .collect(Collectors.toList()));
        playerRepository.save(player);
        gameStateRepository.save(gameState);
        GameStateWrapper gameStateWrapper = new GameStateWrapper();
        gameStateWrapper.setCardsOnTable(gameState.getCardsOnTable());
        gameStateWrapper.setPlayers(gameState
                .getPlayers()
                .stream()
                .map(PlayerWrapper::new)
                .collect(Collectors.toList())
        );
        gameStateWrapper.setTokens(gameState.getTokensOnTable());
        gameStateWrapper.setNobles(gameState.getNobles());
        gameStateWrapper.setIsItMyTurn(true);
        gameStateWrapper.setIsItReserveTime(true);
        return gameStateWrapper;
    }

    @Override
    public boolean checkReserveCard(String cardId, CurrentUser currentUser) {
        return playerRepository.findFirstByUser(currentUser.getUser()).get().getCardsInHand().size() <= 3;
    }

    @Override
    public String addCardToHand(String cardId, CurrentUser currentUser) {
        Random r = new Random();
        GameState gameState = currentUser.getUser().getGameState();
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();
        Card card = cardRepository.findById(Long.parseLong(cardId)).get();
        player.addCardToHand(card);
        List<Card> cardsOnTable = gameState.getCardsOnTable();
        int cardToChange = cardsOnTable.indexOf(card);
        List<Card> cardList = gameState.getCards();
        List<Card> cardsFromLevelList = cardList.stream()
                .filter(c -> Objects.equals(c.getLevel(), card.getLevel()))
                .collect(Collectors.toList());
        Card newCard = cardsFromLevelList.get(r.nextInt(cardsFromLevelList.size()));
        cardList.remove(newCard);
        gameState.setCards(cardList);
        cardsOnTable.set(cardToChange, newCard);
        gameState.setCardsOnTable(cardsOnTable);
        int i = howManyTokensNeedToBeGivenBack(player);
        if (i == 0) {
            boolean isDialogNeeded = giveNobleToPlayer(player, gameState);
            return "Operation Confirmed";
        } else {
            playerRepository.save(player);
            gameStateRepository.save(gameState);
            return "Give back tokens";
        }
    }

    @Override
    public String addCardToHandFromDeck(String deckNr, CurrentUser currentUser) {
        Random r = new Random();
        GameState gameState = currentUser.getUser().getGameState();
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();
        List<Card> cardList = gameState.getCards();
        List<Card> cardsFromLevelList = cardList.stream()
                .filter(c -> Objects.equals(c.getLevel(),Integer.parseInt(deckNr)))
                .collect(Collectors.toList());
        Card newCard = cardsFromLevelList.get(r.nextInt(cardsFromLevelList.size()));
        cardList.remove(newCard);
        gameState.setCards(cardList);
        player.addCardToHand(newCard);
        int i = howManyTokensNeedToBeGivenBack(player);
        if (i == 0) {
            boolean isDialogNeeded = giveNobleToPlayer(player, gameState);
            return "Operation Confirmed";
        } else {
            playerRepository.save(player);
            gameStateRepository.save(gameState);
            return "Give back tokens";
        }
    }

    private String getNextPlayer(GameState gameState) {
        String lastPlayerName = gameState.getLastPlayerName();
        List<Player> players = gameState.getPlayers();
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
