package cpe.asi.cardforge.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCardDTO implements Serializable {
    private UserDTO user;
    private CardDTO card;
    private int id;
}
