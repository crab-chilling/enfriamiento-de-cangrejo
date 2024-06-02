package fr.crab.user.controller;


import fr.crab.dto.UserDTO;
import fr.crab.entity.Kuser;
import fr.crab.user.repository.UserRepository;
import fr.crab.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Kuser> register(@RequestBody Kuser kUser) {
        log.info("Registering user");
        Kuser savedUser = userService.register(kUser);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getUser(@RequestHeader(name="Authorization") String token) {
        log.info("Getting user info from user id in the token");
        return ResponseEntity.ok(userService.convertToDTO(userService.getUserInfoById(token)));
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestHeader(name="Authorization") String token, @RequestBody UserDTO userDTO) {
        log.info("Updating user info");
        Kuser user = userService.convertToEntity(userDTO);
        if (user.getWallet() < 0) {
            throw new IllegalArgumentException("Wallet cannot be negative");
        }
        return ResponseEntity.ok(userService.convertToDTO(userService.save(user)));
    }

}
