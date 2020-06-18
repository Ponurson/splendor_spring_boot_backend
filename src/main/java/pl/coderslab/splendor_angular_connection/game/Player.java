package pl.coderslab.splendor_angular_connection.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;
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
    @OneToMany
    //    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Card> cards;
    @OneToMany
//    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Noble> nobles;
    @OneToMany
//    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Card> cardsInHand;

    @ElementCollection
    @MapKeyColumn(name = "token_type")
    @MapKeyEnumerated(EnumType.STRING)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<TokenType, Integer> playerTokens;
    private Boolean hasSeenResults;

    public Player(User user) {
        this.user = user;
        this.cards = new ArrayList<>();
        this.cardsInHand = new ArrayList<>();
        this.playerTokens = new HashMap<TokenType, Integer>();
        Arrays.stream(TokenType.values()).forEach(tokenType -> playerTokens.put(tokenType, 0));
        this.points = 0;
        this.setHasSeenResults(false);
    }

    public Map<TokenType, Integer> getMapOfCards() {
        HashMap<TokenType, Integer> cardProductMap = new HashMap<>();
        Arrays.stream(TokenType.values()).forEach(tokenType -> cardProductMap.put(tokenType, 0));
        cards.stream()
                .map(Card::getProduces)
                .forEach(tokenType -> cardProductMap.put(tokenType, cardProductMap.get(tokenType) + 1));
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
        card.getCost().forEach((tokenType, integer) -> {
            int costAdjusted = Math.max(integer - (cardProducts.get(tokenType) != null ? cardProducts.get(tokenType) : 0), 0);
            Integer numOfTokensOwnedByPlayer = playerTokens.get(tokenType);
            if (costAdjusted > numOfTokensOwnedByPlayer){
                payment.put(tokenType, numOfTokensOwnedByPlayer);
                playerTokens.put(tokenType, 0);
                payment.put(TokenType.GOLD,costAdjusted- numOfTokensOwnedByPlayer);
                playerTokens.put(TokenType.GOLD, playerTokens.get(TokenType.GOLD)-(costAdjusted- numOfTokensOwnedByPlayer));
            }else {
                payment.put(tokenType, costAdjusted);
                playerTokens.put(tokenType, numOfTokensOwnedByPlayer - costAdjusted);
            }
        });
        cards.add(card);
        points += card.getPoints();
        return payment;
    }

    public void addNoble(Noble noble) {
        nobles.add(noble);
        points += noble.getPoints();
    }

    public void addCardToHand(Card card) {
        cardsInHand.add(card);
    }
}
