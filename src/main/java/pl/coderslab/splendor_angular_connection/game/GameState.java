package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany
    private List<Player> players;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Card> cards;
    private Integer diamonds;
    private Integer emeralds;
    private Integer rubys;
    private Integer saphires;
    private Integer onyxs;
}
