package fr.crab.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String emailAddress;
    private String password;
    private float wallet;
    private List<CardDTO> cards;
}
