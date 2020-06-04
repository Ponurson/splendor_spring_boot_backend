package pl.coderslab.splendor_angular_connection.game;

import pl.coderslab.splendor_angular_connection.user.User;

import java.util.List;

public interface GameService {
    GameState startGame(List<User> challenged);
}
