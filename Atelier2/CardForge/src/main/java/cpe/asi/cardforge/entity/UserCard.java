package cpe.asi.cardforge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "user_card")
public class UserCard {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Kuser user;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
