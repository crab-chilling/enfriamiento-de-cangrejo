package fr.crab.chilling.storeservice.api;

import fr.crab.chilling.dto.StoreItemDTO;
import fr.crab.chilling.entity.Kuser;
import fr.crab.chilling.entity.StoreItem;
import fr.crab.chilling.exception.NotFoundException;
import fr.crab.chilling.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StoreItemService {

    private final StoreItemRepository storeItemRepository;
    private final ModelMapper modelMapper;
    private final WebClient webClient;

    public StoreItemService(StoreItemRepository storeItemRepository) {
        this.modelMapper = new ModelMapper();
        this.storeItemRepository = storeItemRepository;
        this.webClient = WebClient.builder().build();
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
        webClient.get()
                .uri("http://localhost:8080/user/" + idBuyer)
                .retrieve()
                .bodyToMono(Kuser.class)
                .block();
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
        Optional<StoreItem> optCard = storeItemRepository.findById(id);

        if (optCard.isEmpty()) {
            throw new NotFoundException("Card not found");
        }

        String[] chunks = token.split("\\.");
        String base64EncodedBody = chunks[1];
        Long idSeller = JwtUtils.getJwtIdFromBody(base64EncodedBody, new Base64(true));
        // replace by call
        Optional<Kuser> optSeller = userRepository.findById(idSeller);

        if (optSeller.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        boolean possesCard = optSeller.get().getCards().contains(optCard.get().getCard());
        if (!possesCard) {
            throw new NotFoundException("User does not own card");
        }

        StoreItem storeItem = new StoreItem();
        storeItem.setCard(optCard.get().getCard());
        storeItem.setPrice(price);
        storeItem.setUser(optSeller.get());

        return storeItemRepository.save(storeItem);
    }

    public StoreItem add(StoreItem storeItem) {
        return storeItemRepository.save(storeItem);
    }

    public StoreItem get(Long id){
        return storeItemRepository.findById(id).orElseThrow(() -> new NotFoundException("Store item not found"));
    }

    public List<StoreItem> getAll(){
        return storeItemRepository.findAll();
    }

    public void delete(Long id){
        storeItemRepository.deleteById(id);
    }

}
