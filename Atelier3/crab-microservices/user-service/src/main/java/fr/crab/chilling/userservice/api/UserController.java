package fr.crab.chilling.userservice.api;

import fr.crab.chilling.exception.AlreadyExistingException;
import fr.crab.chilling.dto.UserDTO;
import fr.crab.chilling.entity.Kuser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAll() {
        log.info("Getting all users");
        List<Kuser> users = userRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map(userService::convertToDTO).toList();
        return ResponseEntity.ok(userDTOS);
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        log.info("Registering user");
        Kuser user = userService.register(userDTO);
        return ResponseEntity.ok(userService.convertToDTO(user));
    }


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        log.info("Logging in user");
        Optional<Kuser> user = userRepository.findByEmailAddress(userDTO.getEmailAddress());

        if (user.isEmpty() || !user.get().getPassword().equals(userDTO.getPassword())) {
            throw new AlreadyExistingException("Invalid credentials");
        }

        return ResponseEntity.ok(userService.convertToDTO(user.get()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.info("Getting user with id {}", id);
        Optional<Kuser> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new AlreadyExistingException("User not found");
        }

        return ResponseEntity.ok(userService.convertToDTO(user.get()));
    }

}

