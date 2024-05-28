package fr.crab.chilling.cardservice.api;

import fr.crab.chilling.dto.CardDTO;
import fr.crab.chilling.entity.Card;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/add")
    public ResponseEntity<CardDTO> add(@RequestBody CardDTO cardDTO) {
        log.info("Adding card");
        Card savedCard = cardService.add(cardDTO);
        return ResponseEntity.ok(cardService.convertToDTO(savedCard));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> get(@PathVariable Long id) {
        log.info("Getting card with id {}", id);
        Optional<Card> card = cardRepository.findById(id);

        return card.map(value -> ResponseEntity.ok(cardService.convertToDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting card with id {}", id);
        cardRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

