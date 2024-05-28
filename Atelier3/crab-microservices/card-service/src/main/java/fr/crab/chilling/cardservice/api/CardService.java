package fr.crab.chilling.cardservice.api;

import fr.crab.chilling.dto.CardDTO;
import fr.crab.chilling.entity.Card;
import fr.crab.chilling.exception.AlreadyExistingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {

    ModelMapper modelMapper;
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.modelMapper = new ModelMapper();
        this.cardRepository = cardRepository;
    }

    public Card convertToEntity(CardDTO cardDTO) {
        return modelMapper.map(cardDTO, Card.class);
    }

    public CardDTO convertToDTO(Card card) {
        return modelMapper.map(card, CardDTO.class);
    }

    public Card add(CardDTO cardDTO){
        Card card = this.convertToEntity(cardDTO);
        Optional<Card> existingCard = cardRepository.findByName(card.getName());

        if (existingCard.isPresent()) {
            throw new AlreadyExistingException("Card with name already exists");
        }

        return cardRepository.save(card);
    }
}