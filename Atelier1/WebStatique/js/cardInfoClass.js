class CardInfo {
    name;
    description;
    family;
    affinity;
    imgUrl;
    smallImgUrl;
    id;
    energy;
    hp;
    attack;
    defense;
    price;
    userId;

    constructor(){}

    setAttribute(key, value) {
        if(typeof key !== "undefined" || typeof key === "function"){
            this[key] = value;
        }
    }

    hydrate(object){
        for(const [key, value] of Object.entries(object)) {
            this.setAttribute(key, value);
        }
    }

    get name() {
        this.name;
    }

    get description() {
        this.description;
    }

    get family(){
        this.family;
    }
    get affinity(){
        this.affinity;
    }
    get imgUrl(){
        this.imgUrl;
    }

    get smallImgUrl(){
        this.smallImgUrl;
    }
    get id(){
        this.id;
    }
    get energy(){
        this.energy;
    }
    get hp(){
        this.hp;
    }
    get attack(){
        this.attack;
    }
    get defense(){
        this.defense;
    }
    get price(){
        this.price;
    }
    get userId(){
        this.userId;
    }

    set name(name) {
        this.name = name;
    }

    set description(description) {
        this.description = description;
    }

    set family(family){
        this.family = family;
    }
    set affinity(affinity){
        this.affinity = affinity;
    }
    set imgUrl(imgUrl){
        this.imgUrl = imgUrl;
    }

    set smallImgUrl(smallImgUrl){
        this.smallImgUrl = smallImgUrl;
    }
    set id(id){
        this.id = id;
    }
    set energy(energy){
        this.energy = energy;
    }
    set hp(hp){
        this.hp = hp;
    }
    set attack(attack){
        this.attack = attack;
    }
    set defense(defense){
        this.defense = defense;
    }
    set price(price){
        this.price = price;
    }
    set userId(userId){
        this.userId = userId;
    }

}