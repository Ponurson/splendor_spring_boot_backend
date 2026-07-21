package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.splendor_angular_connection.user.User;

import jakarta.persistence.*;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "games")
public class GameState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Player> players;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Card> cards;
    @ManyToMany(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "position")
    private Map<Integer,Card> cardsOnTable;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Noble> nobles;
    private String lastPlayerName;
    // set when the current player clicks gold, cleared by upkeep when the turn's action completes;
    // persisted so a page refresh does not silently drop the player out of reserve mode
    private boolean reserveTime;
    @OneToMany(fetch = FetchType.EAGER)
    private List<User> userList;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "token_type")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<TokenType, Integer> tokensOnTable;

    public void addPayment(Map<TokenType, Integer> payment){
       payment.forEach((tokenType, integer) -> tokensOnTable.put(tokenType, tokensOnTable.get(tokenType)+integer));
    }

}
