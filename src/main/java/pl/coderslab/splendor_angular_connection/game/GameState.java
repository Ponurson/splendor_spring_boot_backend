package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
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
    //    @OneToMany
//    private List<Player> players;
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
    private Integer diamonds;
    private Integer emeralds;
    private Integer rubys;
    private Integer saphires;
    private Integer onyxs;

    public void addPayment(Map<String, Integer> payment) {
        diamonds += payment.get("diamonds");
        emeralds += payment.get("emeralds");
        rubys += payment.get("rubys");
        saphires += payment.get("saphires");
        onyxs += payment.get("onyxs");
    }
}
