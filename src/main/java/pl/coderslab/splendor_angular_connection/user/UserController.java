package pl.coderslab.splendor_angular_connection.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.splendor_angular_connection.auth.CurrentUser;
import pl.coderslab.splendor_angular_connection.auth.LoginResponse;

import java.util.HashMap;

@CrossOrigin
@RestController
public class UserController {
 
    private final UserService userService;
 
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public LoginResponse createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new LoginResponse("user registered");
    }

    @GetMapping("/admin")
    @ResponseBody
    public String userInfo(@AuthenticationPrincipal CurrentUser customUser) {
        User entityUser = customUser.getUser();
        return "Hello " + entityUser.getUsername();
    }

}