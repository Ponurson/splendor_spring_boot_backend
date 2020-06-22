package pl.coderslab.splendor_angular_connection.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.auth.LoginResponse;
import pl.coderslab.splendor_angular_connection.game.Player;
import pl.coderslab.splendor_angular_connection.game.PlayerRepository;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    public final PlayerRepository playerRepository;

    @PostMapping("/register")
    public LoginResponse createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new LoginResponse("user registered");
    }

    @GetMapping("/login")
    public LoginResponse logIn(@AuthenticationPrincipal CurrentUser customUser) {
        return new LoginResponse("login successful");
    }

    @PostMapping("/veryStrangeLogout")
//    tutaj jest problem bo jak stwierdzić że ktoś się wylogował jak zamknął przeglądarke?
//    na razie nie będę tego używał, może się to da obejść
    public LoginResponse logOut(@AuthenticationPrincipal CurrentUser customUser) {
        userService.changeState(customUser, "logged_out");
        userService.clearPreviousGames(customUser);
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
        User user = customUser.getUser();
        String userState = user.getUserState();
        responseMap.put("state", userState);
        if ("host".equals(userState)) {
            userService.checkInvites(user);
        } else if ("challenged".equals(userState)) {
            responseMap.put("challenger", userRepository.
                    findById(user
                            .getCurrentlyInteractingUsers()
                            .get(0)
                    ).get()
                    .getUsername());
        }
        user.setLastOnline(LocalDateTime.now());
        userRepository.save(user);
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
    @GetMapping("/userList")
    public List<String> userList(@AuthenticationPrincipal CurrentUser currentUser){
        userService.changeState(currentUser, "idle");
        userService.clearPreviousGames(currentUser);
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserState().equals("idle") &&
                        ChronoUnit.SECONDS.between(LocalDateTime.from(user.getLastOnline()),LocalDateTime.now()) < 4)
                .map(user -> user.getUsername())
                .collect(Collectors.toList());
    }
}