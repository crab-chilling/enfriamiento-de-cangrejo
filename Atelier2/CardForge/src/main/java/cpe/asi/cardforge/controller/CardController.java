package cpe.asi.cardforge.controller;

import cpe.asi.cardforge.dto.CardDTO;
import cpe.asi.cardforge.entity.Card;
import cpe.asi.cardforge.entity.Kuser;
import cpe.asi.cardforge.repository.CardRepository;
import cpe.asi.cardforge.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/card")
public class CardController {

    private final CardRepository cardRepository;
    private final CardService cardService;

    public CardController(CardRepository cardRepository, CardService cardService) {
        this.cardRepository = cardRepository;
        this.cardService = cardService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CardDTO>> getAll() {
        log.info("Getting all cards");
        List<Card> cards = cardRepository.findAll();
        List<CardDTO> cardDTOS = cards.stream().map(cardService::convertToDTO).toList();
        return ResponseEntity.ok(cardDTOS);
    }

}
