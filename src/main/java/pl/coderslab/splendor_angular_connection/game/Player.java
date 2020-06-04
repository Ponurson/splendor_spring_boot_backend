package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.splendor_angular_connection.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;
    @OneToOne
    private User user;
    private Integer points;
    @ManyToMany
    private List<Card> cards;
    private Integer diamonds;
    private Integer emeralds;
    private Integer rubys;
    private Integer saphires;
    private Integer onyxs;

    public Player(User user) {
        this.user = user;
        this.cards = new ArrayList<>();
        this.diamonds = 0;
        this.emeralds = 0;
        this.rubys = 0;
        this.saphires = 0;
        this.onyxs = 0;
        this.points = 0;
    }
}
