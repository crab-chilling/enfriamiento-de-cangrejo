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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    ModelMapper modelMapper;

    UserRepository userRepository;

    Random random;

    public UserService(UserRepository userRepository) {
        this.modelMapper = new ModelMapper();
        this.userRepository = userRepository;
        this.random = new Random();
    }

    public Kuser convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, Kuser.class);
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
        List<Card> listCard = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Long cardId = this.random.nextLong() % 35;
            Card card = new Card();
            card.setId(cardId);
        }
        user.setCards(listCard);

        return userRepository.save(user);
    }

}
