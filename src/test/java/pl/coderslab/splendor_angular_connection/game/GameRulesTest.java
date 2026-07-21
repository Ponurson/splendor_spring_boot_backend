package pl.coderslab.splendor_angular_connection.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.user.User;
import pl.coderslab.splendor_angular_connection.utils.Utils;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// pure-domain checks for the token rules, no Spring context needed
class GameRulesTest {

    private GameServiceImpl gameService;
    private GameState gameState;
    private Player alicePlayer;
    private CurrentUser alice;

    /** A card nobody can afford at the start of the game. */
    private Card expensiveCard(long id) {
        return Card.builder().id(id).level(1).points(0)
                .cost(Map.of(TokenType.RUBY, 7)).build();
    }

    @BeforeEach
    void setUp() {
        User aliceUser = User.builder().id(1L).username("alice").build();
        User bobUser = User.builder().id(2L).username("bob").build();
        alicePlayer = new Player(aliceUser);
        Player bobPlayer = new Player(bobUser);
        HashMap<TokenType, Integer> tokensOnTable = new HashMap<>();
        Arrays.stream(TokenType.values()).forEach(t -> tokensOnTable.put(t, 4));
        tokensOnTable.put(TokenType.GOLD, 5);
        gameState = GameState.builder()
                .players(List.of(alicePlayer, bobPlayer))
                .tokensOnTable(tokensOnTable)
                .cardsOnTable(new HashMap<>(Map.of(0, expensiveCard(1L), 1, expensiveCard(2L))))
                .nobles(new ArrayList<>())
                .lastPlayerName("init")
                .build();
        aliceUser.setGameState(gameState);
        alice = new CurrentUser("alice", "x", List.of(), aliceUser);
        PlayerRepository playerRepository = mock(PlayerRepository.class);
        when(playerRepository.findFirstByUser(any())).thenReturn(Optional.of(alicePlayer));
        GameStateRepository gameStateRepository = mock(GameStateRepository.class);
        when(gameStateRepository.findById(any())).thenReturn(Optional.of(gameState));
        gameService = new GameServiceImpl(gameStateRepository, null, playerRepository, null, null, new Utils());
    }

    private Map<String, Object> payload(String... colors) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < colors.length; i++) {
            map.put("t" + i, colors[i]);
        }
        return map;
    }

    @Test
    void goldCannotBeTakenAsTwoTokens() {
        assertThat(gameService.checkTokenGain("GOLD", alice)).isFalse();
        assertThat(gameService.checkTokenGain("RUBY", alice)).isTrue();
    }

    @Test
    void twoSameTokensNeedAtLeastFourOnTable() {
        gameState.getTokensOnTable().put(TokenType.RUBY, 3);
        assertThat(gameService.checkTokenGain("RUBY", alice)).isFalse();
    }

    @Test
    void threeTokensMustBeThreeDistinctNonGoldColors() {
        assertThat(gameService.checkTokenGain(
                payload("RUBY", "EMERALD", "ONYX"), alice)).isTrue();
        // duplicate color hidden under different keys
        assertThat(gameService.checkTokenGain(
                payload("RUBY", "RUBY", "EMERALD", "ONYX"), alice)).isFalse();
        // gold smuggled as an extra entry
        assertThat(gameService.checkTokenGain(
                payload("RUBY", "EMERALD", "ONYX", "GOLD"), alice)).isFalse();
    }

    @Test
    void mixedGainMatchesAvailableColorsExactly() {
        // only two non-gold piles left
        gameState.getTokensOnTable().putAll(Map.of(
                TokenType.RUBY, 2, TokenType.EMERALD, 1, TokenType.DIAMOND, 0,
                TokenType.SAPPHIRE, 0, TokenType.ONYX, 0));
        assertThat(gameService.checkMixedTokenGain(payload("RUBY", "EMERALD"), alice)).isTrue();
        assertThat(gameService.checkMixedTokenGain(payload("RUBY", "RUBY"), alice)).isFalse();
        assertThat(gameService.checkMixedTokenGain(payload("RUBY", "EMERALD", "GOLD"), alice)).isFalse();
    }

    @Test
    void playerOverTokenLimitMayOnlyReturnTheExactExcess() {
        alicePlayer.getPlayerTokens().put(TokenType.RUBY, 8);
        alicePlayer.getPlayerTokens().put(TokenType.ONYX, 4);
        // 12 tokens held: every normal action is blocked until the excess is returned
        assertThat(gameService.checkTokenGain("RUBY", alice)).isFalse();
        assertThat(gameService.checkTokenGain(payload("RUBY", "EMERALD", "ONYX"), alice)).isFalse();
        assertThat(gameService.checkTokensReturn(List.of(TokenType.RUBY), alice)).isFalse();
        assertThat(gameService.checkTokensReturn(List.of(TokenType.RUBY, TokenType.ONYX), alice)).isTrue();
        // cannot return tokens the player does not hold
        assertThat(gameService.checkTokensReturn(List.of(TokenType.EMERALD, TokenType.ONYX), alice)).isFalse();
    }

    @Test
    void returningTokensAtOrUnderLimitIsNotAFreeTurnPass() {
        assertThat(gameService.checkTokensReturn(List.of(), alice)).isFalse();
    }

    @Test
    void notYourTurnMeansNoTokenGain() {
        gameState.setLastPlayerName("alice");
        assertThat(gameService.checkTokenGain("RUBY", alice)).isFalse();
    }

    @Test
    void reserveModeSurvivesAReload() {
        assertThat(gameService.addGoldToken(alice).getIsItReserveTime()).isTrue();
        // getFullStateAtInit is what a page refresh calls: the mode must still be there,
        // and every table card stays pickable even though none of them is affordable
        GameStateWrapper afterReload = gameService.getFullStateAtInit(alice);
        assertThat(afterReload.getIsItReserveTime()).isTrue();
        assertThat(afterReload.getCardsOnTable()).allMatch(Card::getClickable);
    }

    @Test
    void reserveModeDoesNotLeakToTheOtherPlayer() {
        gameService.addGoldToken(alice);
        User bobUser = gameState.getPlayers().get(1).getUser();
        bobUser.setGameState(gameState);
        CurrentUser bob = new CurrentUser("bob", "x", List.of(), bobUser);
        assertThat(gameService.getFullStateAtInit(bob).getIsItReserveTime()).isFalse();
    }

    @Test
    void completingTheTurnEndsReserveMode() {
        gameService.addGoldToken(alice);
        gameService.upkeep(gameState, alicePlayer);
        assertThat(gameState.isReserveTime()).isFalse();
        assertThat(gameService.getFullStateAtInit(alice).getIsItReserveTime()).isFalse();
    }
}
