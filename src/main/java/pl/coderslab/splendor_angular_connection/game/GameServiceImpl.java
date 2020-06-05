package pl.coderslab.splendor_angular_connection.game;

import org.springframework.stereotype.Service;
import pl.coderslab.splendor_angular_connection.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        Random r = new Random();
        List<Player> players = challenged.stream()
                .map(user -> new Player(user))
                .peek(player -> playerRepository.save(player))
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
                cardsOnTable.add(card);
            }
        }
        gameState.setCards(cardList);
        gameState.setCardsOnTable(cardsOnTable);
        gameState.setLastPlayerName("init");
        gameState = gameStateRepository.save(gameState);
        return gameState;
    }
}
