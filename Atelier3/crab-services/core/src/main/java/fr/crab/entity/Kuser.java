package fr.crab.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "kuser")
public class Kuser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_name", unique = true)
    private String userName;
    @Column(name = "email_address", unique = true)
    private String emailAddress;
    @Column(name = "password")
    private String password;
    @Column(name = "wallet")
    private float wallet;
    @Column(name = "role")
    private String role;

    @ManyToMany
    @JoinTable(
            name = "user_card",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards;
}
