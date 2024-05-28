package fr.crab.chilling.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StoreItemDTO implements Serializable {
    private Long id;
    private CardDTO card;
    private UserDTO user;
    private float price;
}
