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
public class PlayerWrapper {
    private String playerName;
    private Integer points;
    private Map<TokenType, Integer> tokens;
    private String cardsOwnedShort;
    private List<Card> cards;

    public PlayerWrapper(Player player) {
        this.playerName = player.getUser().getUsername();
        this.points = player.getPoints();
        this.tokens = player.getPlayerTokens();
        this.cardsOwnedShort = player.getMapOfCards().toString();
        this.cards = player.getCards();
    }
}