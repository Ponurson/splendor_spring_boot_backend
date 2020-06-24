package pl.coderslab.splendor_angular_connection.utils;

import pl.coderslab.splendor_angular_connection.game.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static List<Card> mapToList(Map<Integer, Card> map) {
        ArrayList<Card> cards = new ArrayList<>();
        map.forEach((integer, card) -> cards.add(integer, card));
        return cards;
    }
    public static Map<Integer, Card> listToMap(List<Card> cards){
        HashMap<Integer, Card> map = new HashMap<>();
        for (int i = 0; i < cards.size(); i++) {
            map.put(i,cards.get(i));
        }
        return map;
    }
}
