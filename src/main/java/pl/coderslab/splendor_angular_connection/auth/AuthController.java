package pl.coderslab.splendor_angular_connection.auth;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AuthController {
    @GetMapping("/user")
    public LoginResponse user() {
       return new LoginResponse("Login successful");
    }
}