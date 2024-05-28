package fr.crab.chilling.userservice.api;

import fr.crab.chilling.exception.AlreadyExistingException;
import fr.crab.chilling.dto.UserDTO;
import fr.crab.chilling.entity.Kuser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.modelMapper = new ModelMapper();
        this.userRepository = userRepository;
    }

    public Kuser convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, Kuser.class);
    }

    public UserDTO convertToDTO(Kuser user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public Kuser register(UserDTO userDTO) {
        Kuser user = this.convertToEntity(userDTO);
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

        return userRepository.save(user);
    }

}
