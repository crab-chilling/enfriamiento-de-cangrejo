package cpe.asi.cardforge.service;

import cpe.asi.cardforge.dto.StoreItemDTO;
import cpe.asi.cardforge.entity.Card;
import cpe.asi.cardforge.entity.Kuser;
import cpe.asi.cardforge.entity.StoreItem;
import cpe.asi.cardforge.error.NotFoundException;
import cpe.asi.cardforge.repository.CardRepository;
import cpe.asi.cardforge.repository.StoreItemRepository;
import cpe.asi.cardforge.repository.UserRepository;
import cpe.asi.cardforge.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class StoreItemService {

    private final StoreItemRepository storeItemRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    public StoreItemService(UserRepository userRepository, StoreItemRepository storeItemRepository, CardRepository cardRepository) {
        this.modelMapper = new ModelMapper();
        this.storeItemRepository = storeItemRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public StoreItemDTO convertToDTO(StoreItem storeItem) {
        return modelMapper.map(storeItem, StoreItemDTO.class);
    }

    public StoreItem convertToEntity(StoreItemDTO storeItemDTO) {
        return modelMapper.map(storeItemDTO, StoreItem.class);
    }

    public StoreItem purchase(String token, Long id) throws IOException {
        Optional<StoreItem> optItem = storeItemRepository.findById(id);
        if (optItem.isEmpty()) {
            throw new NotFoundException("Store item not found");
        }

        String[] chunks = token.split("\\.");
        String base64EncodedBody = chunks[1];
        Long idBuyer = JwtUtils.getJwtIdFromBody(base64EncodedBody, new Base64(true));
        Optional<Kuser> optBuyer = userRepository.findById(idBuyer);

        if (optBuyer.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        float price = optItem.get().getPrice();
        float wallet = optBuyer.get().getWallet();

        if (wallet < price) {
            throw new NotFoundException("Not enough money");
        }

        optBuyer.get().setWallet(wallet - price);
        userRepository.save(optBuyer.get());
        Kuser seller = optItem.get().getUser();
        seller.setWallet(seller.getWallet() + price);
        userRepository.save(seller);
        storeItemRepository.delete(optItem.get());
        return optItem.get();
    }

    public StoreItem sell(String token, float price, Long id) throws IOException {
        Optional<Card> optCard = cardRepository.findById(id);

        if (optCard.isEmpty()) {
            throw new NotFoundException("Card not found");
        }

        String[] chunks = token.split("\\.");
        String base64EncodedBody = chunks[1];
        Long idSeller = JwtUtils.getJwtIdFromBody(base64EncodedBody, new Base64(true));
        Optional<Kuser> optSeller = userRepository.findById(idSeller);

        if (optSeller.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        boolean possesCard = optSeller.get()
                .getCards()
                .stream()
                .map(Card::getId)
                .anyMatch(cid -> Objects.equals(cid, optCard.get().getId()));
        if (!possesCard) {
            throw new NotFoundException("User does not own card");
        }

        StoreItem storeItem = new StoreItem();
        storeItem.setCard(optCard.get());
        storeItem.setPrice(price);
        storeItem.setUser(optSeller.get());

        return storeItemRepository.save(storeItem);
    }
}
