package pl.coderslab.splendor_angular_connection.game;

import org.springframework.stereotype.Service;
import pl.coderslab.splendor_angular_connection.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService{
    private final GameStateRepository gameStateRepository;
    private final CardRepository cardRepository;
    private final PlayerRepository playerRepository;

    public GameServiceImpl(GameStateRepository gameStateRepository, CardRepository cardRepository, PlayerRepository playerRepository) {
        this.gameStateRepository = gameStateRepository;
        this.cardRepository = cardRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public GameState startGame(List<User> challenged) {
        List<Player> players = challenged.stream()
                .map(user -> new Player(user))
                .peek(player -> playerRepository.save(player))
                .collect(Collectors.toList());
        GameState.GameStateBuilder gameStateBuilder = new GameState().builder();
        GameState gameState = gameStateBuilder
                .cards(cardRepository.findAll())
                .players(players)
                .build();
        gameState = gameStateRepository.save(gameState);
        return gameState;
    }
}
