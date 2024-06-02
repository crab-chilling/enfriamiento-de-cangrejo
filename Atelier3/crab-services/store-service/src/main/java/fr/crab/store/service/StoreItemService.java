package fr.crab.store.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.crab.dto.CardDTO;
import fr.crab.dto.StoreItemDTO;
import fr.crab.dto.UserDTO;
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

    public CardDTO convertToDTO(Card card) {
        return modelMapper.map(card, CardDTO.class);
    }

    public StoreItem convertToEntity(StoreItemDTO storeItemDTO) {
        return modelMapper.map(storeItemDTO, StoreItem.class);
    }

    public Kuser convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, Kuser.class);
    }

    public Card convertToEntity(CardDTO cardDTO) {
        return modelMapper.map(cardDTO, Card.class);
    }

    public StoreItem purchase(String token, Long id) throws IOException {
        Optional<StoreItem> optItem = storeItemRepository.findById(id);
        if (optItem.isEmpty()) {
            throw new NotFoundException("Store item not found");
        }

        String[] chunks = token.split("\\.");
        String base64EncodedBody = chunks[1];
        Long idBuyer = JwtUtils.getJwtIdFromBody(base64EncodedBody, new Base64(true));
        Kuser buyer = fetchUserFromId(idBuyer, token);
        if (buyer == null) {
            throw new NotFoundException("User not found");
        }

        float price = optItem.get().getPrice();
        float wallet = buyer.getWallet();

        if (wallet < price) {
            throw new NotFoundException("Not enough money");
        }

        buyer.setWallet(wallet - price);
        buyer = this.convertToEntity(this.callUpdateUser(buyer, token));
        Kuser seller = optItem.get().getUser();
        seller.setWallet(seller.getWallet() + price);
        seller = this.convertToEntity(this.callUpdateUser(seller, token));
        storeItemRepository.delete(optItem.get());
        return optItem.get();
    }

    public StoreItem sell(String token, float price, Long cardId) throws IOException {

        Card card = fetchCardFromId(cardId, token);

        if (card == null) {
            throw new NotFoundException("Card not found");
        }

        String[] chunks = token.split("\\.");
        String base64EncodedBody = chunks[1];
        Long idSeller = JwtUtils.getJwtIdFromBody(base64EncodedBody, new Base64(true));
        Kuser seller = fetchUserFromId(idSeller, token);

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

    public List<StoreItem> getSellerStoreCards(Long sellerId, String token) throws JsonProcessingException {
        Kuser seller = fetchUserFromId(sellerId, token);

        if (seller == null) {
            throw new NotFoundException("User not found");
        }

        return storeItemRepository.findAllByUser(seller);
    }

    private Kuser fetchUserFromId(long id, String token) {
        Mono<UserDTO> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8082)
                        .path("/user/")
                        .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserDTO.class);
        return this.convertToEntity(result.block());
    }

    private Card fetchCardFromId(long id, String token) {
        Mono<CardDTO> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8080)
                        .path("/card/" + id)
                        .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(CardDTO.class);
        return this.convertToEntity(result.block());
    }

    private UserDTO callUpdateUser(Kuser kuser, String token) {
        return webClient.patch()
                .uri("http://localhost:8082/user/update")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(BodyInserters.fromValue(kuser))
                .retrieve().bodyToMono(UserDTO.class).block();
    }
}
