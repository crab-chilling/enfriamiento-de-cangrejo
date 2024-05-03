package cpe.enfriamiento.de.cangrejo.CardForge.controller;

import cpe.enfriamiento.de.cangrejo.CardForge.dto.CardDto;
import cpe.enfriamiento.de.cangrejo.CardForge.model.Card;
import cpe.enfriamiento.de.cangrejo.CardForge.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @RequestMapping(value = "/card/{id}", method = RequestMethod.GET)
    public String show(Model model, @PathVariable int id) {
        Card card = cardService.getCardById(id);
        if (card == null) {
            return "notFound";
        }
        model.addAttribute("card", card);
        return "card";
    }

    @RequestMapping(value = { "/card/create"}, method = RequestMethod.GET)
    public String create(Model model) {
        CardDto cardForm = new CardDto();
        model.addAttribute("cardForm", cardForm);
        return "cardForm";
    }

    @RequestMapping(value = { "/card/create"}, method = RequestMethod.POST)
    public String create(Model model, @ModelAttribute("cardForm") CardDto cardForm) {
        int userId = 1; // assuming we do not have an user management yet...
        double price = 10.0; // assuming that there is no price field in the form
        Card card = cardService.createCard(cardForm.getName(), cardForm.getDescription(), cardForm.getFamily(),
                cardForm.getAffinity(), cardForm.getImgUrl(), cardForm.getImgUrl(), cardForm.getEnergy(),
                cardForm.getHp(), cardForm.getDefence(), cardForm.getAttack(), price, userId);
        model.addAttribute("card", card);
        return "card";
    }
}
