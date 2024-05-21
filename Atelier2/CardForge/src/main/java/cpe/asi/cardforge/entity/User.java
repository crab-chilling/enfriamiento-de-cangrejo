package cpe.asi.cardforge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "kuser")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    @OneToMany
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private List<UserCard> deck;
}
