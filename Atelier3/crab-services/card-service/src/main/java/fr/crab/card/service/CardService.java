package fr.crab.card.service;

import fr.crab.card.repository.CardRepository;
import fr.crab.dto.CardDTO;
import fr.crab.entity.Card;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CardService {

    ModelMapper modelMapper;
    private final CardRepository cardRepository;

    private final Random random;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
        this.modelMapper = new ModelMapper();
        this.random = new Random();
    }


    public Card convertToEntity(CardDTO cardDTO) {
        return modelMapper.map(cardDTO, Card.class);
    }

    public CardDTO convertToDTO(Card card) {
        return modelMapper.map(card, CardDTO.class);
    }

    public List<Card> generate(int amount) {
        List<Card> list = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            cardRepository.findById(random.nextLong() % 35).ifPresent(list::add);
        }
        return list;
    }
}
