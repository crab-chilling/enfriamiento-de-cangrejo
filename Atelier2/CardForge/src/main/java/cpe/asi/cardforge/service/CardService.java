package cpe.asi.cardforge.service;

import cpe.asi.cardforge.dto.CardDTO;
import cpe.asi.cardforge.entity.Card;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    ModelMapper modelMapper;

    public CardService() {
        this.modelMapper = new ModelMapper();
    }

    public Card convertToEntity(CardDTO cardDTO) {
        return modelMapper.map(cardDTO, Card.class);
    }

    public CardDTO convertToDTO(Card card) {
        return modelMapper.map(card, CardDTO.class);
    }
}
