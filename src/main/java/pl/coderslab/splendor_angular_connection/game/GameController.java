package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.auth.LoginResponse;

import java.util.HashMap;
import java.util.List;
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
            String tokens = gameService.addTokens(token, currentUser);
            return new LoginResponse(tokens);
        }
        return new LoginResponse("bad request");
    }

    @PostMapping("/gainThreeTokens")
    public LoginResponse gainThreeTokens(@AuthenticationPrincipal CurrentUser currentUser,
                                         @RequestBody Map<String, Object> token) {
        if (gameService.checkTokenGain(token, currentUser)) {
            String tokens = gameService.addTokens(token, currentUser);
            return new LoginResponse(tokens);
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

    @PostMapping("/gainMixedTokens")
    public LoginResponse gainMixedTokens(@AuthenticationPrincipal CurrentUser currentUser,
                                         @RequestBody Map<String, Object> token) {
        if (gameService.checkMixedTokenGain(token, currentUser)) {
            String tokens = gameService.addTokens(token, currentUser);
            return new LoginResponse(tokens);
        }
        return new LoginResponse("bad request");
    }
    @PostMapping("/returnTokensToTable")
    public LoginResponse returnTokensToTable(@AuthenticationPrincipal CurrentUser currentUser,
                                             @RequestBody List<TokenType> tokens){
        if (gameService.checkTokensReturn(tokens, currentUser)) {
            String answer = gameService.returnTokens(tokens, currentUser);
            return new LoginResponse(answer);
        }
        return new LoginResponse("bad request");
    }
    @PostMapping("/gainGoldToken")
    public GameStateWrapper gainGoldToken(@AuthenticationPrincipal CurrentUser currentUser){
        if (gameService.checkGoldToken(currentUser)) {
            GameStateWrapper gameState = gameService.addGoldToken(currentUser);
            return gameState;
        }
        return gameService.getFullStateAtInit(currentUser);
    }
    @PostMapping("/reserveCard")
    public LoginResponse reserveCard(@AuthenticationPrincipal CurrentUser currentUser,
                                     @RequestBody String cardId){
        if (gameService.checkReserveCard(cardId, currentUser)) {
            String response = gameService.addCardToHand(cardId, currentUser);
            return new LoginResponse(response);
        }
        return new LoginResponse("bad request");
    }
    @PostMapping("/reserveCardFromDeck")
    public LoginResponse reserveCardFromDeck(@AuthenticationPrincipal CurrentUser currentUser,
                                             @RequestBody String deckNr) {
        if (gameService.checkReserveCard(deckNr, currentUser)) {
            String response = gameService.addCardToHandFromDeck(deckNr, currentUser);
            return new LoginResponse(response);
        }
        return new LoginResponse("bad request");
    }

}
