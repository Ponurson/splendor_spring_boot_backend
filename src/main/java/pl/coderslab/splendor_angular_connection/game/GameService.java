package pl.coderslab.splendor_angular_connection.game;

import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.user.User;

import java.util.List;
import java.util.Map;

public interface GameService {
    GameState startGame(List<User> challenged);

    GameState checkPossibleActions(GameState gameState, Player player);

    GameStateWrapper getFullStateAtInit(CurrentUser currentUser);

    boolean checkTokenGain(String token, CurrentUser currentUser);

    boolean checkTokenWithGetter(String token, GameState gameState, int i);

    boolean checkTokenGain(Map<String, Object> token, CurrentUser currentUser);

    void addTokens(String token, CurrentUser currentUser);

    void addTokens(Map<String, Object> token, CurrentUser currentUser);

    boolean checkBuyCard(String cardId, CurrentUser currentUser);

    void buyCard(String cardId, CurrentUser currentUser);

    boolean checkMixedTokenGain(Map<String, Object> token, CurrentUser currentUser);
}
