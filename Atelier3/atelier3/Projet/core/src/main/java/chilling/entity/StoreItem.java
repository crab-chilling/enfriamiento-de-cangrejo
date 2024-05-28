package fr.crab.chilling.entity;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity(name = "store_item")
public class StoreItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Card card;

    @Column(name = "price", nullable = false)
    private float price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Kuser user;
}
