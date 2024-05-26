package cpe.asi.cardforge.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StoreItemDTO implements Serializable {
    private CardDTO card;
    private UserDTO user;
}
