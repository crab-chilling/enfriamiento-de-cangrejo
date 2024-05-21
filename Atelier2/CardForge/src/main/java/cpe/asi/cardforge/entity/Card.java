package cpe.asi.cardforge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "family")
    private String family;
    @Column(name = "affinity")
    private String affinity;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "miniature_url")
    private String miniatureUrl;
    @Column(name = "energy")
    private int energy;
    @Column(name = "health")
    private int health;
    @Column(name = "attack")
    private int attack;
    @Column(name = "defence")
    private int defence;
    @Column(name = "price")
    private float price;

    @ManyToOne
    @MapKey(name = "id")
    private UserCard deck;
}
