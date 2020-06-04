package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;
    private Integer points;
    private String produces;
    private Integer level;
    private Integer diamondCost;
    private Integer emeraldCost;
    private Integer rubyCost;
    private Integer saphireCost;
    private Integer onyxCost;
    private String graphic;

}
