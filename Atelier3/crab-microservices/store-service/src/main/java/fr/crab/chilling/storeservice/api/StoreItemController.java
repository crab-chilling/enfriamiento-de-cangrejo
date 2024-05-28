package fr.crab.chilling.storeservice.api;


import fr.crab.chilling.dto.StoreItemDTO;
import fr.crab.chilling.entity.StoreItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store-item")
public class StoreItemController {

    private final StoreItemService storeItemService;
    public StoreItemController(StoreItemService storeItemService) {
        this.storeItemService = storeItemService;
    }

    @GetMapping("/all")
    public List<StoreItemDTO> getAll() {
        log.info("Getting all store items");
        return storeItemService
                .getAll()
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

        return storeItemService.convertToDTO(storeItemService.sell(token, storeItemDTO.getPrice(), storeItemDTO.getId()));
    }


    @GetMapping("/{id}")
    public StoreItemDTO getStoreItem(@PathVariable Long id) {
        log.info("Getting store item");
        return storeItemService.convertToDTO(storeItemService.get(id));
    }

    @DeleteMapping("/{id}")
    public void deleteStoreItem(@PathVariable Long id) {
        log.info("Deleting store item");
        storeItemService.delete(id);
    }

    @PostMapping("/")
    public StoreItemDTO addStoreItem(@RequestBody StoreItemDTO storeItemDTO) {
        log.info("Adding store item");
        StoreItem item = storeItemService.add(storeItemService.convertToEntity(storeItemDTO));
        return storeItemService.convertToDTO(item);
    }

    @PutMapping("/")
    public StoreItemDTO updateStoreItem(@RequestBody StoreItemDTO storeItemDTO) {
        log.info("Updating store item");
        StoreItem item = storeItemService.add(storeItemService.convertToEntity(storeItemDTO));
        return storeItemService.convertToDTO(item);
    }


}

