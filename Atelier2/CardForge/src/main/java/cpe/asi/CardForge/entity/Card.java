package cpe.asi.CardForge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Card {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imageUrl;
    private String miniatureUrl;
    private int energy;
    private int health;
    private int attack;
    private int defence;
    private float price;
    @ManyToOne
    private Deck deck;
}
