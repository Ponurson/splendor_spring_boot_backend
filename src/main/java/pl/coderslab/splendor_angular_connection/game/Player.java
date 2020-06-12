package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.coderslab.splendor_angular_connection.user.User;

import javax.persistence.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

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

    @ElementCollection
    @MapKeyColumn(name = "token_type")
    @MapKeyEnumerated(EnumType.STRING)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<TokenType, Integer> playerTokens;

    public Player(User user) {
        this.user = user;
        this.cards = new ArrayList<>();
        this.playerTokens = new HashMap<TokenType, Integer>();
        Arrays.stream(TokenType.values()).forEach(tokenType -> playerTokens.put(tokenType,0));
        this.points = 0;
    }

    public Map<TokenType, Integer> getMapOfCards() {
        HashMap<TokenType, Integer> cardProductMap = new HashMap<>();
        Arrays.stream(TokenType.values()).forEach(tokenType -> cardProductMap.put(tokenType,0));
        cards.stream()
                .map(Card::getProduces)
                .forEach(tokenType -> cardProductMap.put(tokenType,cardProductMap.get(tokenType)+1));
        return cardProductMap;
    }


    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", points=" + points +
                ", cards=" + getMapOfCards() +
                ", diamonds=" + getPlayerTokens().get(TokenType.DIAMOND) +
                ", emeralds=" + getPlayerTokens().get(TokenType.EMERALD) +
                ", rubys=" + getPlayerTokens().get(TokenType.RUBY) +
                ", sapphires=" + getPlayerTokens().get(TokenType.SAPPHIRE) +
                ", onyxs=" + getPlayerTokens().get(TokenType.ONYX) +
                '}';
    }

    public Map<TokenType, Integer> addCard(Card card) {
        HashMap<TokenType, Integer> payment = new HashMap<>();
        Map<TokenType, Integer> cardProducts = getMapOfCards();
        cards.add(card);
        points += card.getPoints();
        card.getCost().forEach((tokenType, integer) -> {
            payment.put(tokenType,
                    Math.max(integer - (cardProducts.get(tokenType) != null ? cardProducts.get(tokenType) : 0), 0)
                    );
            playerTokens.put(tokenType,playerTokens.get(tokenType) - payment.get(tokenType));
        });
        return payment;
    }
}