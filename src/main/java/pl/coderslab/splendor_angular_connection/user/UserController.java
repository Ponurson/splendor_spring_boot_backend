package pl.coderslab.splendor_angular_connection.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.auth.LoginResponse;
import pl.coderslab.splendor_angular_connection.utils.Utils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@SessionAttributes({"players"})
public class UserController {

    private final UserService userService;
    private final Utils utils;

    public UserController(UserService userService, Utils utils) {
        this.userService = userService;
        this.utils = utils;
    }

    @PostMapping("/register")
    public LoginResponse createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new LoginResponse("user registered");
    }

    @GetMapping("/login")
    public LoginResponse logIn(@AuthenticationPrincipal CurrentUser customUser) {
        HashMap<String, String> stateMap = new HashMap<>();
        stateMap.put("state","idle");
        userService.changeState(customUser,utils.stringify(stateMap));
        return new LoginResponse("login successful");
    }

    @GetMapping("/logout")
//    tutaj jest problem bo jak stwierdzić że ktoś się wylogował jak zamknął przeglądarke?
//    na razie nie będę tego używał, może się to da obejść
    public LoginResponse logOut(@AuthenticationPrincipal CurrentUser customUser) {
        HashMap<String, String> stateMap = new HashMap<>();
        stateMap.put("state","logged_out");
        userService.changeState(customUser,utils.stringify(stateMap));
        return new LoginResponse("logout successful");
    }
//    tutaj trzeba się upewnić że przynajmniej jeden gracz istnieje bo jak nie
//    no to trzeba graczowi zwrócic jakiś błąd, ale to później
    @PostMapping("/invite")
    public LoginResponse inviteForGame(@RequestBody HashMap<String, Object> invitation,
                                       @AuthenticationPrincipal CurrentUser customUser) {
        userService.startChallenge(invitation, customUser);
        return new LoginResponse("challenge started");
    }

    @GetMapping("/userState")
    public Map<String,String> getUserState(@AuthenticationPrincipal CurrentUser customUser
                                           ) {


        return utils.jsonify(customUser.getUser().getUserState());
    }

    @GetMapping("/admin")
    public String userInfo(@AuthenticationPrincipal CurrentUser customUser) {
        User entityUser = customUser.getUser();
        return "Hello " + entityUser.getUsername();
    }
    @PostMapping("/resignFromGame")
    public LoginResponse resignFromGame(@RequestBody HashMap<String,Object> data,
                                        @AuthenticationPrincipal CurrentUser currentUser){
        userService.resignFromGame(data, currentUser);
        return new LoginResponse("resigned from game");
    }
    @PostMapping("/joinGame")
    public LoginResponse joinGame(@RequestBody HashMap<String,Object> data,
                                  @AuthenticationPrincipal CurrentUser currentUser){
        userService.joinGame(data, currentUser);
        return new LoginResponse("joined game");
    }


}