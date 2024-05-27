package cpe.asi.cardforge.controller;

import cpe.asi.cardforge.dto.UserCardDTO;
import cpe.asi.cardforge.dto.UserDTO;
import cpe.asi.cardforge.entity.Card;
import cpe.asi.cardforge.entity.Kuser;
import cpe.asi.cardforge.error.NotFoundException;
import cpe.asi.cardforge.repository.UserRepository;
import cpe.asi.cardforge.service.CardService;
import cpe.asi.cardforge.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user-card")
public class UserCardController {


    private final UserRepository userRepository;
    private final UserService userService;
    private final CardService cardService;

    public UserCardController(UserRepository userRepository, UserService userService, CardService cardService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.cardService = cardService;
    }

    @PostMapping("/acquire")
    public ResponseEntity<UserDTO> acquireCard(@RequestBody UserCardDTO userCardDTO) {
        log.info("Acquiring card");
        Kuser user = userService.convertToEntity(userCardDTO.getUser());
        Card card = cardService.convertToEntity(userCardDTO.getCard());

        Optional<Kuser> existingUser = userRepository.findById(user.getId());

        if (existingUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        existingUser.get().getCards().add(card);

        Kuser savedUser = userRepository.save(existingUser.get());

        return ResponseEntity.ok(userService.convertToDTO(savedUser));
    }
}
