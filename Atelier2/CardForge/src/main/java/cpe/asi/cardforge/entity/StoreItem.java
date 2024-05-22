package cpe.asi.cardforge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "store_item")
public class StoreItem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToMany
    @PrimaryKeyJoinColumn(name = "id")
    private List<Card> cards;

    @Column(name = "price", nullable = false)
    private float price;
}
