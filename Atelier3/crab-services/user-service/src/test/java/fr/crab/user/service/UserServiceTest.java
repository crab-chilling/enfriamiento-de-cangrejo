package fr.crab.user.service;

import fr.crab.dto.UserDTO;
import fr.crab.entity.Kuser;
import fr.crab.user.UserApplication;
import fr.crab.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserApplication.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void convertToEntity() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmailAddress("test@test.com");
        userDTO.setPassword("password");
        userDTO.setWallet(1000);
        Kuser user = userService.convertToEntity(userDTO);
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmailAddress(), user.getEmailAddress());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getWallet(), user.getWallet());
    }

    @Test
    void convertToDTO() {
        Kuser user = new Kuser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmailAddress("test@test.com");
        user.setPassword("password");
        user.setWallet(1000);
        UserDTO userDTO = userService.convertToDTO(user);
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getEmailAddress(), userDTO.getEmailAddress());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getWallet(), userDTO.getWallet());
    }

    @Test
    void getUserInfoById() {
        Kuser user = new Kuser();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmailAddress("test@test.com");
        user.setPassword("password");
        user.setWallet(1000);
        userRepository.save(user);

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcklkIjoxLCJpYXQiOjE1MTYyMzkwMjJ9.h12IDcRnMHxnZK1CAgdd_dkpm3pC9tKGvUUhro3I23Y";

        Kuser found = userService.getUserInfoById(token);

        Assertions.assertNotNull(found);
    }

    @Test
    void save() {
    }

    @Test
    void register() {
    }
}