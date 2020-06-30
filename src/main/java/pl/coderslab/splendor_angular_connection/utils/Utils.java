package pl.coderslab.splendor_angular_connection.utils;

import org.springframework.stereotype.Component;
import pl.coderslab.splendor_angular_connection.game.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Utils {
    public List<Card> mapToList(Map<Integer, Card> map) {
        ArrayList<Card> cards = new ArrayList<>();
        map.forEach(cards::add);
        return cards;
    }
    public Map<Integer, Card> listToMap(List<Card> cards){
        HashMap<Integer, Card> map = new HashMap<>();
        for (int i = 0; i < cards.size(); i++) {
            map.put(i,cards.get(i));
        }
        return map;
    }
}
