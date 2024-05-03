package cpe.enfriamiento.de.cangrejo.CardForge.service;

import cpe.enfriamiento.de.cangrejo.CardForge.model.Card;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    private final List<Card> cards;

    public CardService() {
        Card c1 = new Card("name1", "description", "family", "affinity", "image.jpeg", "image.jpeg", 1, 1, 1, 1, 10.0, 1);
        Card c2 = new Card("name2", "description", "family", "affinity", "image.jpeg", "image.jpeg", 1, 1, 1, 1, 10.0, 1);
        Card c3 = new Card("name3", "description", "family", "affinity", "image.jpeg", "image.jpeg", 1, 1, 1, 1, 10.0, 1);

        cards = new ArrayList<>();
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
    }

    public Card getCardById(int id) {
        for (Card c : cards) {
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

    public Card createCard(String name, String description, String family, String affinity, String imgUrl, String smallImgUrl, int energy, int hp, int defence, int attack, double price, int userId) {
        Card c = new Card(name, description, family, affinity, imgUrl, smallImgUrl, energy, hp, defence, attack, price, userId);
        cards.add(c);
        return c;
    }
}
