package pl.coderslab.splendor_angular_connection.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private long id;
    private String username;
    private String password;
    private String token;
}
