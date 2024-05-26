package cpe.asi.cardforge.controller;

import cpe.asi.cardforge.dto.CardDTO;
import cpe.asi.cardforge.dto.StoreItemDTO;
import cpe.asi.cardforge.entity.Card;
import cpe.asi.cardforge.entity.Kuser;
import cpe.asi.cardforge.entity.StoreItem;
import cpe.asi.cardforge.error.NotFoundException;
import cpe.asi.cardforge.repository.CardRepository;
import cpe.asi.cardforge.repository.StoreItemRepository;
import cpe.asi.cardforge.repository.UserRepository;
import cpe.asi.cardforge.security.JwtUtils;
import cpe.asi.cardforge.service.StoreItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/store-item")
public class StoreItemController {

    private final StoreItemService storeItemService;
    private final StoreItemRepository storeItemRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public StoreItemController(StoreItemService storeItemService, StoreItemRepository storeItemRepository,UserRepository userRepository, CardRepository cardRepository) {
        this.storeItemService = storeItemService;
        this.storeItemRepository = storeItemRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    @GetMapping("/all")
    public List<StoreItemDTO> getAll() {
        log.info("Getting all store items");
        return storeItemRepository
                .findAll()
                .stream()
                .map(storeItemService::convertToDTO)
                .toList();
    }

    @PostMapping("/purchase/{id}")
    public StoreItemDTO purchase(@RequestHeader(name="Authorization") String token, @PathVariable Long id) throws IOException {
        log.info("Purchasing store item");
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

        return storeItemService.convertToDTO(optItem.get());
    }

    @PostMapping("/sell/{price}")
    public StoreItemDTO sell(@RequestHeader(name="Authorization") String token, @RequestBody CardDTO cardDTO, @PathVariable float price) throws IOException {
        log.info("Selling store item");
        Optional<Card> optCard = cardRepository.findById(cardDTO.getId());

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

        boolean possesCard = optSeller.get().getCards().contains(optCard.get());
        if (!possesCard) {
            throw new NotFoundException("User does not own card");
        }

        StoreItem storeItem = new StoreItem();
        storeItem.setCard(optCard.get());
        storeItem.setPrice(price);
        storeItem.setUser(optSeller.get());

        storeItemRepository.save(storeItem);

        return storeItemService.convertToDTO(storeItem);
    }


}
