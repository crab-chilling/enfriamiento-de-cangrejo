package cpe.asi.CardForge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Deck {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "deck")
    private List<Card> cards;
}
