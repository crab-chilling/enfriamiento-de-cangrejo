package cpe.enfriamiento.de.cangrejo.CardMarket.dto;

public class CardDto {
    private String name;
    private String description;
    private String family;
    private String affinity;
    private String imgUrl;
    private int energy;
    private int hp;
    private int defence;
    private int attack;
    private double price;

    public CardDto() {
        this.name = "";
        this.description = "";
        this.family = "";
        this.affinity = "";
        this.imgUrl = "";
        this.energy = 0;
        this.hp = 0;
        this.defence = 0;
        this.attack = 0;
        this.price = 0;
    }

    public CardDto(String name, String description, String family, String affinity, String imgUrl, int energy, int hp, int defence, int attack, int price) {
        this.name = name;
        this.description = description;
        this.family = family;
        this.affinity = affinity;
        this.imgUrl = imgUrl;
        this.energy = energy;
        this.hp = hp;
        this.defence = defence;
        this.attack = attack;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getAffinity() {
        return affinity;
    }

    public void setAffinity(String affinity) {
        this.affinity = affinity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
