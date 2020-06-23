package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.core.annotation.Order;
import pl.coderslab.splendor_angular_connection.user.User;

import javax.persistence.*;
import java.util.HashMap;
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
    @OneToMany(cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Player> players;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Card> cards;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Card> cardsOnTable;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Noble> nobles;
    private String lastPlayerName;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> userList;

    @ElementCollection
    @MapKeyColumn(name = "token_type")
    @MapKeyEnumerated(EnumType.STRING)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<TokenType, Integer> tokensOnTable;

    public void addPayment(Map<TokenType, Integer> payment){
       payment.forEach((tokenType, integer) -> tokensOnTable.put(tokenType, tokensOnTable.get(tokenType)+integer));
    }

}
