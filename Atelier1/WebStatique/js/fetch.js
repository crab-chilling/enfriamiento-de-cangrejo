document.getElementById("form-card").addEventListener("click", function(event) {
    event.preventDefault();
})

function generate(){

    const GET_CHUCK_URL="https://api.chucknorris.io/jokes/random"; 
    let context =   {
                        method: 'GET'
                    };
        
    fetch(GET_CHUCK_URL,context)
        .then(response => response.json())
            .then(response => callback(response))
            .catch(error => err_callback(error));
}

function callback(response){
    document.getElementById("txtChuck").innerHTML = response.value;
}

function err_callback(error){
    console.log(error);
}

function submit_card(){
    var formulaire = document.getElementById("form-card");
    const cardInfo = new CardInfo();
    for (var i = 0; i < formulaire.elements.length; i++) {
        var element = formulaire.elements[i];
        cardInfo.setAttribute(element.name, element.value);
    }
    const POST_CARD_URL = "http://tp.cpe.fr:8083/card";
    let context = {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(cardInfo),
    };
    fetch(POST_CARD_URL, context)
    .then(response => response.json())
    .then(response => console.log(JSON.stringify(response)));
}