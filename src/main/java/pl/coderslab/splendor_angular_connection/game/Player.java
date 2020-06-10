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
    private Integer diamonds;
    private Integer emeralds;
    private Integer rubys;
    private Integer saphires;
    private Integer onyxs;

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
        this.diamonds = 0;
        this.emeralds = 0;
        this.rubys = 0;
        this.saphires = 0;
        this.onyxs = 0;
        this.points = 0;
    }

    public Map<TokenType, Integer> getMapOfCards() {
        HashMap<TokenType, Integer> cardProductMap = new HashMap<>();
        Arrays.stream(TokenType.values()).forEach(tokenType -> cardProductMap.put(tokenType,0));
        cards.stream()
                .map(Card::getProduces)
                .forEach(tokenType -> cardProductMap.put(tokenType,cardProductMap.get(tokenType)+1));
        return cardProductMap;
//        return cards.stream()
//                .map(Card::getProduces)
//                .collect(groupingBy(Function.identity(), summingInt(e -> 1)));
    }

    ;

    public Map<String, Integer> getTokensMap() {
        HashMap<String, Integer> tokenMap = new HashMap<>();
        tokenMap.put("diamond", diamonds);
        tokenMap.put("emerald", emeralds);
        tokenMap.put("ruby", rubys);
        tokenMap.put("saphire", saphires);
        tokenMap.put("onyx", onyxs);
        return tokenMap;
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
//
//        payment.put("diamonds",
//                Math.max(card.getDiamondCost() - (cardProducts.get("diamond") != null ? cardProducts.get("diamond") : 0), 0));
//        diamonds -= payment.get("diamonds");
//        payment.put("emeralds",
//                Math.max(card.getEmeraldCost() - (cardProducts.get("emerald") != null ? cardProducts.get("emerald") : 0), 0));
//        emeralds -= payment.get("emeralds");
//        payment.put("rubys",
//                Math.max(card.getRubyCost() - (cardProducts.get("ruby") != null ? cardProducts.get("ruby") : 0), 0));
//        rubys -= payment.get("rubys");
//        payment.put("saphires",
//                Math.max(card.getSaphireCost() - (cardProducts.get("saphire") != null ? cardProducts.get("saphire") : 0), 0));
//        saphires -= payment.get("saphires");
//        payment.put("onyxs",
//                Math.max(card.getOnyxCost() - (cardProducts.get("onyx") != null ? cardProducts.get("onyx") : 0), 0));
//        onyxs -= payment.get("onyxs");
        return payment;
    }
}
