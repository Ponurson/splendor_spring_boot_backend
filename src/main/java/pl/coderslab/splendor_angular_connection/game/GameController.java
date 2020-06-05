package pl.coderslab.splendor_angular_connection.game;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;

import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/game")
public class GameController {
    private final GameStateRepository gameStateRepository;

    public GameController(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    @GetMapping("/getState")
    public HashMap<String, String> getGameState(@AuthenticationPrincipal CurrentUser currentUser) {
        HashMap<String, String> responseMap = new HashMap<>();
        String gameState = currentUser.getUser().getGameState().getLastPlayerName();
        responseMap.put("state", gameState);
        return responseMap;
    }

    @GetMapping("/getFullState")
    public GameStateWrapper getFullState(@AuthenticationPrincipal CurrentUser currentUser) {
        GameState gameState = gameStateRepository.findById(currentUser.getUser().getGameState().getId()).get();
        GameStateWrapper gameStateWrapper = new GameStateWrapper();
        gameStateWrapper.setCardsOnTable(gameState
                .getCardsOnTable()
                .stream()
                .map(Card::toString)
                .collect(Collectors.toList())
        );
        return gameStateWrapper;
    }
}
