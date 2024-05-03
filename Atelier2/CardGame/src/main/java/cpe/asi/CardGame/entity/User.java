package cpe.asi.CardGame.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String emailAddress;
    private String password;
    private float wallet;
    @OneToOne
    private Deck deck;
}
