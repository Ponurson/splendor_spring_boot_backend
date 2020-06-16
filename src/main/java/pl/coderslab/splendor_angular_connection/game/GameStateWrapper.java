package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class GameStateWrapper {
    private List<Card> cardsOnTable;
    private List<PlayerWrapper> players;
    private Map<TokenType,Integer> tokens;
    private Boolean isItMyTurn;
    private List<Noble> nobles;
    private Boolean isItReserveTime;
}

