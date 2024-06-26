package cpe.asi.cardforge.service;

import cpe.asi.cardforge.dto.UserDTO;
import cpe.asi.cardforge.entity.Kuser;
import cpe.asi.cardforge.error.NotFoundException;
import cpe.asi.cardforge.repository.UserRepository;
import cpe.asi.cardforge.security.JwtUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    ModelMapper modelMapper;

    UserRepository userRepository;

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

    public Kuser getUserInfoById(String token)
    {
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
}
