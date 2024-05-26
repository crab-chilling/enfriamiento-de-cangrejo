package cpe.asi.cardforge.controller;

import cpe.asi.cardforge.dto.UserDTO;
import cpe.asi.cardforge.entity.Kuser;
import cpe.asi.cardforge.error.AlreadyExistingException;
import cpe.asi.cardforge.repository.UserRepository;
import cpe.asi.cardforge.service.UserService;
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
        Kuser user = userService.convertToEntity(userDTO);
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

        Kuser savedUser = userRepository.save(user);
        return ResponseEntity.ok(userService.convertToDTO(savedUser));
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