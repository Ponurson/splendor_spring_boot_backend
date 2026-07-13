package pl.coderslab.splendor_angular_connection.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String message;
    private String token;

    public LoginResponse(String message) {
        this.message = message;
    }

    public LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }
}
