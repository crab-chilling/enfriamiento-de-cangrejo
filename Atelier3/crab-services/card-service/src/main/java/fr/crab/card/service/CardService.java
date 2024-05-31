package fr.crab.card.service;

import fr.crab.card.repository.CardRepository;
import fr.crab.dto.CardDTO;
import fr.crab.entity.Card;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    ModelMapper modelMapper;
    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
        this.modelMapper = new ModelMapper();
    }


    public Card convertToEntity(CardDTO cardDTO) {
        return modelMapper.map(cardDTO, Card.class);
    }

    public CardDTO convertToDTO(Card card) {
        return modelMapper.map(card, CardDTO.class);
    }
}
