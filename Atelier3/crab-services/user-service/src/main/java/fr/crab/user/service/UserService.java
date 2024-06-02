package fr.crab.user.service;


import fr.crab.dto.CardDTO;
import fr.crab.dto.UserDTO;
import fr.crab.entity.Card;
import fr.crab.entity.Kuser;
import fr.crab.error.AlreadyExistingException;
import fr.crab.error.NotFoundException;
import fr.crab.user.repository.UserRepository;
import fr.crab.utils.JwtUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    ModelMapper modelMapper;

    UserRepository userRepository;

    Random random;

    private static final WebClient webClient = WebClient.create();

    public UserService(UserRepository userRepository) {
        this.modelMapper = new ModelMapper();
        this.userRepository = userRepository;
        this.random = new Random();
    }

    public Kuser convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, Kuser.class);
    }

    private Card convertToEntity(CardDTO cardDTO) {
        return modelMapper.map(cardDTO, Card.class);
    }

    public UserDTO convertToDTO(Kuser user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public Kuser getUserInfoById(String token) {
        try {
            String[] chunks = token.split("\\.");
            if (chunks.length < 2) {
                throw new IllegalArgumentException("Invalid JWT token format.");
            }

            String base64EncodedBody = chunks[1];
            Long userId = JwtUtils.getJwtIdFromBody(base64EncodedBody, new Base64(true));
            Optional<Kuser> user = userRepository.findById(userId);

            return user.orElseThrow(() -> new NotFoundException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user from token", e);
        }
    }

    public Kuser save(Kuser user) {
        return userRepository.save(user);
    }

    public Kuser update(Kuser user) {
        Optional<Kuser> existingUser = userRepository.findByUserName(user.getUserName());
        if(existingUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        user.setId(existingUser.get().getId());
        user.setRole(existingUser.get().getRole());
        return userRepository.save(user);
    }

    private List<CardDTO> fetchAmountOfCard(int count){
        return webClient.get()
                .uri("http://localhost:8011/card/generate/" + count)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CardDTO>>() {
                })
                .block();
    }

    public Kuser register(Kuser user) {
        Optional<Kuser> existingUserByEmail = userRepository.findByEmailAddress(user.getEmailAddress());

        if (existingUserByEmail.isPresent()) {
            throw new AlreadyExistingException("User with email already exists");
        }

        Optional<Kuser> existingUserByUserName = userRepository.findByUserName(user.getUserName());
        if (existingUserByUserName.isPresent()) {
            throw new AlreadyExistingException("User with email already exists");
        }

        if (user.getRole() == null) {
            user.setRole("USER");
        }

        // generate 5 random number that will be the user's card
        List<CardDTO> dtos = this.fetchAmountOfCard(5);

        if(CollectionUtils.isEmpty(dtos)) {
            throw new NotFoundException("No cards found");
        }

        user.setCards(dtos.stream().map(this::convertToEntity).toList());

        return userRepository.save(user);
    }

}
