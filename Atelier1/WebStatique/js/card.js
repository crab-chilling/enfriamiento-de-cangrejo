fetch("http://tp.cpe.fr:8083/cards")
.then(response => response.json())
.then(json => loadCards(json))

function loadCards(cardlist) {

    let template = document.querySelector("#selectedCard");

    for(const card of cardlist){

        let cardInfo = new CardInfo();
        cardInfo.hydrate(card);

        let clone = document.importNode(template.content, true);
    
        newContent= clone.firstElementChild.innerHTML
                    .replace(/{{family_src}}/g, cardInfo.imgUrl)
                    .replace(/{{family_name}}/g, cardInfo.name)
                    .replace(/{{image_src}}/g, cardInfo.imgUrl)
                    .replace(/{{date}}/g, "N")
                    .replace(/{{comment}}/g, cardInfo.description)
                    .replace(/{{like}}/g, "N")
                    .replace(/{{button}}/g, "Read");
        clone.firstElementChild.innerHTML= newContent;
    
        let cardContainer= document.querySelector("#gridContainer");
        cardContainer.appendChild(clone);
    }
}





