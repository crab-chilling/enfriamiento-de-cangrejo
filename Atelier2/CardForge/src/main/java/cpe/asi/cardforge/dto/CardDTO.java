package cpe.asi.cardforge.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CardDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imageUrl;
    private String miniatureUrl;
    private int energy;
    private int health;
    private int attack;
    private int defence;
}
