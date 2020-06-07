package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.auth.LoginResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/game")
@AllArgsConstructor
public class GameController {
    private final GameStateRepository gameStateRepository;
    private final GameService gameService;

    @GetMapping("/getState")
    public HashMap<String, String> getGameState(@AuthenticationPrincipal CurrentUser currentUser) {
        HashMap<String, String> responseMap = new HashMap<>();
        String gameState = currentUser.getUser().getGameState().getLastPlayerName();
        responseMap.put("state", gameState);
        return responseMap;
    }

    @GetMapping("/getFullState")
    public GameStateWrapper getFullState(@AuthenticationPrincipal CurrentUser currentUser) {
        return gameService.getFullStateAtInit(currentUser);
    }

    @PostMapping("/gainTwoTokens")
    public LoginResponse gainTwoTokens(@AuthenticationPrincipal CurrentUser currentUser,
                                       @RequestBody String token) {
        if (gameService.checkTokenGain(token, currentUser)) {
            gameService.addTokens(token, currentUser);
            return new LoginResponse("gained two tokens");
        }
        return new LoginResponse("bad request");
    }

    @PostMapping("/gainThreeTokens")
    public LoginResponse gainThreeTokens(@AuthenticationPrincipal CurrentUser currentUser,
                                         @RequestBody Map<String, Object> token) {
        if (gameService.checkTokenGain(token, currentUser)) {
            gameService.addTokens(token, currentUser);
            return new LoginResponse("gained three tokens");
        }
        return new LoginResponse("bad request");
    }

    @PostMapping("/buyCard")
    public LoginResponse buyCard(@AuthenticationPrincipal CurrentUser currentUser,
                                 @RequestBody String cardId) {
        if (gameService.checkBuyCard(cardId, currentUser)) {
            gameService.buyCard(cardId, currentUser);
            return new LoginResponse("gained three tokens");
        }
        return new LoginResponse("bad request");
    }
}
