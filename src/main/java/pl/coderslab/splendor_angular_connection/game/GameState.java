package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Long> players;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Card> cards;
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Card> cardsOnTable;
    private String lastPlayerName;

    @ElementCollection
    @MapKeyColumn(name = "token_type")
    @MapKeyEnumerated(EnumType.STRING)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<TokenType, Integer> tokensOnTable;

    public void addPayment(Map<TokenType, Integer> payment){
       payment.forEach((tokenType, integer) -> tokensOnTable.put(tokenType, tokensOnTable.get(tokenType)+integer));
    }

}
