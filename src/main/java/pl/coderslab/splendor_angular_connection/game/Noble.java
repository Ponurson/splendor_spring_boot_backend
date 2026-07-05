package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nobles")
public class Noble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noble_id")
    private Long id;
    private Integer points;
    private String graphic;
    @Transient
    private Boolean clickable;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "token_type")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<TokenType, Integer> cardCombination;


}
