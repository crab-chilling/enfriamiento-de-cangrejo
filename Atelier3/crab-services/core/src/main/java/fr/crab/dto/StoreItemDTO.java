package fr.crab.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StoreItemDTO implements Serializable {
    private Long id;
    private CardDTO card;
    private Long user_id;
    private float price;
}
