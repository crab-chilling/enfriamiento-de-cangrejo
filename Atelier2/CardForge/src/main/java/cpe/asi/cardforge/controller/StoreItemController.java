package cpe.asi.cardforge.controller;

import cpe.asi.cardforge.dto.CardDTO;
import cpe.asi.cardforge.dto.StoreItemDTO;
import cpe.asi.cardforge.entity.StoreItem;
import cpe.asi.cardforge.repository.StoreItemRepository;
import cpe.asi.cardforge.security.JwtUtils;
import cpe.asi.cardforge.service.CardService;
import cpe.asi.cardforge.service.StoreItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store-item")
public class StoreItemController {

    private final StoreItemService storeItemService;
    private final CardService cardService;
    private final StoreItemRepository storeItemRepository;

    public StoreItemController(StoreItemService storeItemService, StoreItemRepository storeItemRepository, CardService cardService) {
        this.storeItemService = storeItemService;
        this.storeItemRepository = storeItemRepository;
        this.cardService = cardService;
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
        return storeItemService.convertToDTO(storeItemService.purchase(token, id));
    }

    @PostMapping("/sell")
    public StoreItemDTO sell(
            @RequestHeader(name="Authorization") String token,
            @RequestBody StoreItemDTO storeItemDTO) throws IOException {
        log.info("Selling store item");
        return storeItemService.convertToDTO(storeItemService.sell(token, storeItemDTO.getPrice(), storeItemDTO.getCard().getId()));
    }

    @GetMapping("/seller/{sellerId}")
    public List<CardDTO> getSellerStoreCards(@PathVariable Long sellerId) {
        log.info("Getting all store items from seller " + sellerId);
        return storeItemService
                .getSellerStoreCards(sellerId)
                .stream().map(s -> cardService.convertToDTO(s.getCard()))
                .toList();
    }
}
