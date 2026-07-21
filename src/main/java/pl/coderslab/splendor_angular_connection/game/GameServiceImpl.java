package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.user.User;
import pl.coderslab.splendor_angular_connection.user.UserRepository;
import pl.coderslab.splendor_angular_connection.utils.Utils;

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
    private final UserRepository userRepository;
    private final Utils utils;

    @Override
    public GameState startGame(List<User> challenged) {
        Random r = new Random();
        Collections.shuffle(challenged);
        List<Player> players = challenged.stream()
                .map(Player::new)
                .peek(playerRepository::save)
                .collect(Collectors.toList());
        GameState gameState = new GameState();
        gameState.setUserList(challenged);
        gameState.setPlayers(players);
        List<Card> cardList = cardRepository.findAll();
        // the level-0 card (id 91) is the "empty slot" placeholder, it must never sit in the deck
        cardList.removeIf(card -> card.getLevel() == 0);
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
        gameState.setCardsOnTable(utils.listToMap(cardsOnTable));
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
        gameState.setCardsOnTable(utils.listToMap(utils.mapToList(gameState.getCardsOnTable()).stream()
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
                .collect(Collectors.toList())));

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
                .collect(Collectors.toSet()));
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
        if (currentUser.getUser().getGameState() == null) {
            return null; // no active game yet -> 200 with empty body instead of NPE
        }
        GameState gameState = gameStateRepository.findById(currentUser.getUser().getGameState().getId()).get();
        GameStateWrapper gameStateWrapper = new GameStateWrapper();
        boolean isItMyTurn = false;
        if ("init".equals(gameState.getLastPlayerName())) {
            isItMyTurn = currentUser.getUsername().equals(gameState.getPlayers().get(0).getUser().getUsername());
        } else {
            isItMyTurn = currentUser.getUsername().equals(getNextPlayer(gameState));
        }
        // reserve mode belongs to whoever is mid-turn, so it only survives a refresh for that player
        boolean isItReserveTime = isItMyTurn && gameState.isReserveTime();
        if (isItMyTurn) {
            gameState = checkPossibleActions(gameState, playerRepository.findFirstByUser(currentUser.getUser()).get());
        }
        if (isItReserveTime) {
            markEveryCardClickable(gameState);
        }
        gameStateWrapper.setCardsOnTable(utils.mapToList(gameState.getCardsOnTable()));
        gameStateWrapper.setPlayers(gameState
                .getPlayers()
                .stream()
                .map(PlayerWrapper::new)
                .collect(Collectors.toList())
        );
        gameStateWrapper.setTokens(gameState.getTokensOnTable());
        gameStateWrapper.setNobles(gameState.getNobles());
        gameStateWrapper.setIsItMyTurn(isItMyTurn);
        gameStateWrapper.setIsItReserveTime(isItReserveTime);
        gameStateWrapper.setCurrentPlayerName(getNextPlayer(gameState));
        return gameStateWrapper;
    }

    @Override
    public boolean checkTokenGain(String token, CurrentUser currentUser) {
        // gold can only be gained by reserving a card, never as a "take two" action
        return isPlayersTurn(currentUser) &&
                !TokenType.GOLD.name().equals(token) &&
                checkTokenWithGetter(token, currentUser.getUser().getGameState(), 3);
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
                .filter(s -> !TokenType.GOLD.name().equals(s))
                .filter(s -> checkTokenWithGetter(s, gameState, 0))
                .collect(Collectors.toSet());
        // token.size() == set.size() rejects duplicated colors and smuggled GOLD entries,
        // addTokens() grants every entry of the request so each one must have been validated
        return isPlayersTurn(currentUser) && set.size() == 3 && token.size() == 3;
    }

    @Override
    public String addTokens(String token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        TokenType tokenType = TokenType.valueOf(token);
        Player player = getPlayerFromGameState(currentUser, gameState);
        Map<TokenType, Integer> playerTokens = player.getPlayerTokens();
        playerTokens.put(tokenType, (playerTokens.get(tokenType) == null ? 0 : playerTokens.get(tokenType)) + 2);
        player.setPlayerTokens(playerTokens);
        Map<TokenType, Integer> tokensOnTable = gameState.getTokensOnTable();
        tokensOnTable.put(tokenType, (tokensOnTable.get(tokenType) == null ? 0 : tokensOnTable.get(tokenType)) - 2);
        gameState.setTokensOnTable(tokensOnTable);
        return upkeep(gameState, player);
    }

    private void giveNobleToPlayer(Player player, GameState gameState) {
        Map<TokenType, Integer> cards = player.getMapOfCards();
        List<Noble> nobles = gameState.getNobles();
        List<Noble> availableNobles = nobles.stream()
                .filter(noble -> Arrays.stream(TokenType.values())
                        .allMatch(tokenType -> cards.get(tokenType) >=
                                (noble.getCardCombination().get(tokenType) != null ? noble.getCardCombination().get(tokenType) : 0)))
                .collect(Collectors.toList());
        if (!availableNobles.isEmpty()) {
            // ponytail: rules let the player pick among multiple nobles; no UI for that, grant the first
            Noble noble = availableNobles.get(0);
            nobles.remove(noble);
            player.addNoble(noble);
            gameState.setNobles(nobles);
        }
        gameState.setLastPlayerName(player.getUser().getUsername());
        playerRepository.save(player);
        gameStateRepository.save(gameState);
    }

    @Override
    public String addTokens(Map<String, Object> token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        Player player = getPlayerFromGameState(currentUser, gameState);
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
        return upkeep(gameState, player);
    }

    public String upkeep(GameState gameState, Player player) {
        // every completed action funnels through here, so this is the one place reserve mode ends
        gameState.setReserveTime(false);
        int i = howManyTokensNeedToBeGivenBack(player);
        if (i == 0) {
            giveNobleToPlayer(player, gameState);
            if (endGame(gameState)) {
                gameState.setLastPlayerName("endGame");
                gameStateRepository.save(gameState);
            }
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
        if (isPlayersTurn(currentUser)) {
            String currentPlayer = currentUser.getUsername();
            GameState gameState1 = checkPossibleActions(gameState,
                    playerRepository.findFirstByUser(currentUser.getUser()).get());
            // clickable is null (not false) on unaffordable cards, plain anyMatch would NPE
            return utils.mapToList(gameState1.getCardsOnTable()).stream()
                    .filter(card -> card.getId().equals(Long.parseLong(cardId)))
                    .anyMatch(card -> Boolean.TRUE.equals(card.getClickable())) || gameState1.getPlayers()
                    .stream()
                    .filter(player -> currentPlayer.equals(player.getUser().getUsername()))
                    .findFirst().get().getCardsInHand().stream()
                    .filter(card -> card.getId().equals(Long.parseLong(cardId)))
                    .anyMatch(card -> Boolean.TRUE.equals(card.getClickable()));
        }
        return false;
    }

    @Override
    public String buyCard(String cardId, CurrentUser currentUser) {
        Random r = new Random();
        GameState gameState = currentUser.getUser().getGameState();
        Player player = getPlayerFromGameState(currentUser, gameState);
        Card card = cardRepository.findById(Long.parseLong(cardId)).get();
        Map<TokenType, Integer> payment = null;
        try {
            payment = player.addCard(card);
            gameState.addPayment(payment);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        List<Card> cardsOnTable = utils.mapToList(gameState.getCardsOnTable());
        int cardToChange = cardsOnTable.indexOf(card);
        if (cardToChange != -1) {
            List<Card> cardList = gameState.getCards();
            List<Card> cardsFromLevelList = cardList.stream()
                    .filter(c -> Objects.equals(c.getLevel(), card.getLevel()))
                    .collect(Collectors.toList());
            if (cardsFromLevelList.size() > 0) {
                Card newCard = cardsFromLevelList.get(r.nextInt(cardsFromLevelList.size()));
                cardList.remove(newCard);
                gameState.setCards(cardList);
                cardsOnTable.set(cardToChange, newCard);
                gameState.setCardsOnTable(utils.listToMap(cardsOnTable));
            } else {
                cardsOnTable.set(cardToChange, cardRepository.findById(91L).get());
                gameState.setCardsOnTable(utils.listToMap(cardsOnTable));
            }
        } else {
            Set<Card> cardsInHand = player.getCardsInHand();
            cardsInHand.remove(card);
            player.setCardsInHand(cardsInHand);
        }
        return upkeep(gameState, player);
    }

    @Override
    public boolean checkMixedTokenGain(Map<String, Object> token, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        Set<String> keySet = token.keySet();
        Set<String> set = keySet.stream()
                .map(s -> (String) token.get(s))
                .filter(s -> !TokenType.GOLD.name().equals(s))
                .filter(s -> checkTokenWithGetter(s, gameState, 0))
                .collect(Collectors.toSet());
        int numberOfEmptyTokens = (int) Arrays.stream(TokenType.values())
                .filter(tokenType -> tokenType != TokenType.GOLD)
                .filter(tokenType -> gameState.getTokensOnTable().get(tokenType) == 0).count();
        // same as checkTokenGain: every sent entry must be a distinct, validated, non-gold color
        return isPlayersTurn(currentUser) &&
                set.size() == Math.min(3, 5 - numberOfEmptyTokens) &&
                token.size() == set.size();
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
        if (!isPlayersTurnIgnoringTokenLimit(currentUser)) {
            return false;
        }
        Player player = playerRepository.findFirstByUser(currentUser.getUser()).get();
        Map<TokenType, Integer> playerTokens = player.getPlayerTokens();
        int debt = howManyTokensNeedToBeGivenBack(player);
        // returning tokens ends the turn (upkeep), so it is only legal when the player
        // is over the limit and returns exactly the excess - otherwise it is a free "pass"
        return debt > 0 && tokens.size() == debt && tokens.stream().distinct()
                .allMatch(tokenType -> playerTokens.getOrDefault(tokenType, 0) >= Collections.frequency(tokens, tokenType));
    }

    @Override
    public String returnTokens(List<TokenType> tokens, CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        Player player = getPlayerFromGameState(currentUser, gameState);
        Map<TokenType, Integer> playerTokens = player.getPlayerTokens();
        Map<TokenType, Integer> tokensOnTable = gameState.getTokensOnTable();
        tokens
                .forEach(tokenType -> {
                    playerTokens.put(tokenType, (playerTokens.get(tokenType) == null ? 0 : playerTokens.get(tokenType)) - 1);
                    tokensOnTable.put(tokenType, (tokensOnTable.get(tokenType) == null ? 0 : tokensOnTable.get(tokenType)) + 1);
                });
        player.setPlayerTokens(playerTokens);
        gameState.setTokensOnTable(tokensOnTable);
        return upkeep(gameState, player);
    }

    @Override
    public boolean checkGoldToken(CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        return isPlayersTurn(currentUser) &&
                getPlayerFromGameState(currentUser, gameState).getCardsInHand().size() < 3;
    }

    @Override
    public GameStateWrapper addGoldToken(CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        // clicking gold again leaves reserve mode - without it a misclick can only be undone by
        // reserving something, and with an empty table and empty decks not even that
        if (gameState.isReserveTime()) {
            gameState.setReserveTime(false);
            gameStateRepository.save(gameState);
            return getFullStateAtInit(currentUser); // rebuilds clickability for the buy-only view
        }
        Player player = getPlayerFromGameState(currentUser, gameState);
        gameState.setReserveTime(true);
        markEveryCardClickable(gameState);
        playerRepository.save(player);
        gameStateRepository.save(gameState);
        GameStateWrapper gameStateWrapper = new GameStateWrapper();
        gameStateWrapper.setCardsOnTable(utils.mapToList(gameState.getCardsOnTable()));
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

    /** In reserve mode any real card on the table is a legal pick, not just the affordable ones. */
    private void markEveryCardClickable(GameState gameState) {
        gameState.setCardsOnTable(utils.listToMap(
                utils.mapToList(gameState.getCardsOnTable())
                        .stream()
                        .map(card -> {
                            if (card.getId() != 91L) {
                                card.setClickable(true);
                            }
                            return card;
                        })
                        .collect(Collectors.toList())));
    }

    @Override
    public boolean checkReserveCard(String cardId, CurrentUser currentUser) {
        return isPlayersTurn(currentUser) &&
                playerRepository.findFirstByUser(currentUser.getUser()).get().getCardsInHand().size() < 3;
    }

    private void giveGoldTokenIfAvailable(GameState gameState, Player player) {
        Map<TokenType, Integer> playerTokens = player.getPlayerTokens();
        Map<TokenType, Integer> tokensOnTable = gameState.getTokensOnTable();
        if (tokensOnTable.get(TokenType.GOLD) > 0) {
            playerTokens.put(TokenType.GOLD, (playerTokens.get(TokenType.GOLD) == null ? 0 : playerTokens.get(TokenType.GOLD)) + 1);
            tokensOnTable.put(TokenType.GOLD, tokensOnTable.get(TokenType.GOLD) - 1);
            player.setPlayerTokens(playerTokens);
            gameState.setTokensOnTable(tokensOnTable);
        }
    }

    @Override
    public String addCardToHand(String cardId, CurrentUser currentUser) {
        Random r = new Random();
        GameState gameState = currentUser.getUser().getGameState();
        Player player = getPlayerFromGameState(currentUser, gameState);

        Card card = cardRepository.findById(Long.parseLong(cardId)).get();
        List<Card> cardsOnTable = utils.mapToList(gameState.getCardsOnTable());
        int cardToChange = cardsOnTable.indexOf(card);
        if (cardToChange == -1 || card.getId() == 91L) {
            return "bad request";
        }
        giveGoldTokenIfAvailable(gameState, player);
        player.addCardToHand(card);
        List<Card> cardList = gameState.getCards();
        List<Card> cardsFromLevelList = cardList.stream()
                .filter(c -> Objects.equals(c.getLevel(), card.getLevel()))
                .collect(Collectors.toList());
        if (cardsFromLevelList.isEmpty()) {
            cardsOnTable.set(cardToChange, cardRepository.findById(91L).get());
        } else {
            Card newCard = cardsFromLevelList.get(r.nextInt(cardsFromLevelList.size()));
            cardList.remove(newCard);
            gameState.setCards(cardList);
            cardsOnTable.set(cardToChange, newCard);
        }
        gameState.setCardsOnTable(utils.listToMap(cardsOnTable));
        return upkeep(gameState, player);
    }

    @Override
    public String addCardToHandFromDeck(String deckNr, CurrentUser currentUser) {
        Random r = new Random();
        GameState gameState = currentUser.getUser().getGameState();
        Player player = getPlayerFromGameState(currentUser, gameState);
        List<Card> cardList = gameState.getCards();
        List<Card> cardsFromLevelList = cardList.stream()
                .filter(c -> Objects.equals(c.getLevel(), Integer.parseInt(deckNr)))
                .collect(Collectors.toList());
        if (cardsFromLevelList.isEmpty()) {
            return "bad request";
        }
        giveGoldTokenIfAvailable(gameState, player);
        Card newCard = cardsFromLevelList.get(r.nextInt(cardsFromLevelList.size()));
        cardList.remove(newCard);
        gameState.setCards(cardList);
        player.addCardToHand(newCard);
        return upkeep(gameState, player);
    }

    @Override
    public boolean endGame(GameState gameState) {
        return gameState.getPlayers().stream().map(Player::getPoints).anyMatch(integer -> integer >= 15) &&
                Objects.equals(gameState.getPlayers().get(0).getUser().getUsername(), getNextPlayer(gameState));

    }

    @Override
    public void gameCleanup(CurrentUser currentUser) {
        User user = currentUser.getUser();
        Player player = playerRepository.findFirstByUser(user).get();
        player.setHasSeenResults(true);
        playerRepository.save(player);
        GameState gameState = user.getGameState();
        gameState.setPlayers(gameState.getPlayers()
                .stream()
                .map(player1 -> {
                    return Objects.equals(player1.getId(), player.getId()) ? player : player1;
                })
                .collect(Collectors.toList()));
        if (gameState.getPlayers().stream().allMatch(Player::getHasSeenResults)) {
            userRepository.saveAll(gameState.getUserList().stream().peek(user1 -> user1.setGameState(null)).collect(Collectors.toList()));
            gameStateRepository.delete(gameState);
        }
        user.setUserState("idle");
        userRepository.save(user);
    }

    private boolean isPlayersTurn(CurrentUser currentUser) {
        // a player over the 10-token limit may only return tokens (checkTokensReturn),
        // any other action while the turn has not passed yet must be rejected
        return isPlayersTurnIgnoringTokenLimit(currentUser) &&
                howManyTokensNeedToBeGivenBack(
                        getPlayerFromGameState(currentUser, currentUser.getUser().getGameState())) == 0;
    }

    private boolean isPlayersTurnIgnoringTokenLimit(CurrentUser currentUser) {
        GameState gameState = currentUser.getUser().getGameState();
        return gameState != null && currentUser.getUsername().equals(getNextPlayer(gameState));
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
    @Override
    public Player getPlayerFromGameState(CurrentUser currentUser, GameState gameState) {
        Optional<Player> optionalPlayer = gameState.getPlayers().stream()
                .filter(player -> player.getUser().getUsername().equals(currentUser.getUsername()))
                .findFirst();
        return optionalPlayer.get();
    }
}
