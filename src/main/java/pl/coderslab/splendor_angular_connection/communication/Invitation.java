package pl.coderslab.splendor_angular_connection.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {
    private String player1;
    private String player2;
    private String player3;
}
