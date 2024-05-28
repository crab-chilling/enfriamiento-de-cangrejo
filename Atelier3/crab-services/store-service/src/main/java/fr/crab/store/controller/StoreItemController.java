package fr.crab.store.controller;


import fr.crab.dto.CardDTO;
import fr.crab.dto.StoreItemDTO;
import fr.crab.store.repository.StoreItemRepository;
import fr.crab.store.service.StoreItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store-item")
public class StoreItemController {

    private final StoreItemService storeItemService;
    private final StoreItemRepository storeItemRepository;

    public StoreItemController(StoreItemService storeItemService, StoreItemRepository storeItemRepository) {
        this.storeItemService = storeItemService;
        this.storeItemRepository = storeItemRepository;
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
        return null;
//        return storeItemService
//                .getSellerStoreCards(sellerId)
//                .stream().map(s -> cardService.convertToDTO(s.getCard()))
//                .toList();
    }
}
