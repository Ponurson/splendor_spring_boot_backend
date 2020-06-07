package pl.coderslab.splendor_angular_connection.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.auth.LoginResponse;


import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public LoginResponse createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new LoginResponse("user registered");
    }

    @GetMapping("/login")
    public LoginResponse logIn(@AuthenticationPrincipal CurrentUser customUser) {
        userService.changeState(customUser, "idle");
        return new LoginResponse("login successful");
    }

    @GetMapping("/logout")
//    tutaj jest problem bo jak stwierdzić że ktoś się wylogował jak zamknął przeglądarke?
//    na razie nie będę tego używał, może się to da obejść
    public LoginResponse logOut(@AuthenticationPrincipal CurrentUser customUser) {
        userService.changeState(customUser, "logged_out");
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
    public Map<String, String> getUserState(@AuthenticationPrincipal CurrentUser customUser) {
        HashMap<String, String> responseMap = new HashMap<>();
        String userState = customUser.getUser().getUserState();
        responseMap.put("state", userState);
        if ("host".equals(userState)) {
            userService.checkInvites(customUser.getUser());
        } else if ("challenged".equals(userState)) {
            responseMap.put("challenger", userRepository.
                    findById(customUser
                            .getUser()
                            .getCurrentlyInteractingUsers()
                            .get(0)
                    ).get()
                    .getUsername());
        }
        return responseMap;
    }

    @GetMapping("/admin")
    public String userInfo(@AuthenticationPrincipal CurrentUser customUser) {
        User entityUser = customUser.getUser();
        return "Hello " + entityUser.getUsername();
    }

    @PostMapping("/resignFromGame")
    public LoginResponse resignFromGame(@RequestBody HashMap<String, Object> data,
                                        @AuthenticationPrincipal CurrentUser currentUser) {
        userService.resignFromGame(data, currentUser);
        return new LoginResponse("resigned from game");
    }

    @PostMapping("/joinGame")
    public LoginResponse joinGame(@RequestBody HashMap<String, Object> data,
                                  @AuthenticationPrincipal CurrentUser currentUser) {
        userService.joinGame(data, currentUser);
        return new LoginResponse("joined game");
    }
}