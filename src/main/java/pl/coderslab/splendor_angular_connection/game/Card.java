package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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
    @Transient
    private Boolean clickable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) &&
                Objects.equals(points, card.points) &&
                Objects.equals(produces, card.produces) &&
                Objects.equals(level, card.level) &&
                Objects.equals(diamondCost, card.diamondCost) &&
                Objects.equals(emeraldCost, card.emeraldCost) &&
                Objects.equals(rubyCost, card.rubyCost) &&
                Objects.equals(saphireCost, card.saphireCost) &&
                Objects.equals(onyxCost, card.onyxCost) &&
                Objects.equals(graphic, card.graphic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, points, produces, level, diamondCost, emeraldCost, rubyCost, saphireCost, onyxCost, graphic);
    }
}
