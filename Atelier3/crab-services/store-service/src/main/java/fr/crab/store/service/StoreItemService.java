package fr.crab.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.crab.dto.StoreItemDTO;
import fr.crab.entity.Card;
import fr.crab.entity.Kuser;
import fr.crab.entity.StoreItem;
import fr.crab.error.NotFoundException;
import fr.crab.store.repository.StoreItemRepository;
import fr.crab.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class StoreItemService {

    private final StoreItemRepository storeItemRepository;
    private final ModelMapper modelMapper;
    private static final WebClient webClient = WebClient.create();

    public StoreItemService(StoreItemRepository storeItemRepository) {
        this.modelMapper = new ModelMapper();
        this.storeItemRepository = storeItemRepository;
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
        Kuser buyer = fetchUserFromId(idBuyer);
        if (buyer == null) {
            throw new NotFoundException("User not found");
        }

        float price = optItem.get().getPrice();
        float wallet = buyer.getWallet();

        if (wallet < price) {
            throw new NotFoundException("Not enough money");
        }

        buyer.setWallet(wallet - price);
        registerUser(buyer);
        Kuser seller = optItem.get().getUser();
        seller.setWallet(seller.getWallet() + price);
        registerUser(seller);
        storeItemRepository.delete(optItem.get());
        return optItem.get();
    }

    public StoreItem sell(String token, float price, Long id) throws IOException {
        Mono<String> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost:8081")
                        .path("/card")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
        String content = result.block();
        Card card =  new ObjectMapper().readValue(content, Card.class);

        if (card == null) {
            throw new NotFoundException("Card not found");
        }

        String[] chunks = token.split("\\.");
        String base64EncodedBody = chunks[1];
        Long idSeller = JwtUtils.getJwtIdFromBody(base64EncodedBody, new Base64(true));
        Kuser seller = fetchUserFromId(idSeller);

        if (seller == null) {
            throw new NotFoundException("User not found");
        }

        boolean possesCard = seller
                .getCards()
                .stream()
                .map(Card::getId)
                .anyMatch(cid -> Objects.equals(cid, card.getId()));
        if (!possesCard) {
            throw new NotFoundException("User does not own card");
        }

        StoreItem storeItem = new StoreItem();
        storeItem.setCard(card);
        storeItem.setPrice(price);
        storeItem.setUser(seller);

        return storeItemRepository.save(storeItem);
    }

    public List<StoreItem> getSellerStoreCards(Long sellerId) throws JsonProcessingException {
        Kuser seller = fetchUserFromId(sellerId);

        if (seller == null) {
            throw new NotFoundException("User not found");
        }

        return storeItemRepository.findAllByUser(seller);
    }

    private Kuser fetchUserFromId(long id) throws JsonProcessingException {
        Mono<String> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost:8081")
                        .path("/user")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
        String content = result.block();
        return new ObjectMapper().readValue(content, Kuser.class);
    }

    private void registerUser(Kuser kuser) {
        webClient.post()
                .uri("http://localhost:8081/user/register")
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(kuser))
                .retrieve().bodyToMono(String.class).block();
    }
}
