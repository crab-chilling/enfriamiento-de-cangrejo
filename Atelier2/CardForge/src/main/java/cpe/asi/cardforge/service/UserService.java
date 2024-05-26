package cpe.asi.cardforge.service;

import cpe.asi.cardforge.dto.UserDTO;
import cpe.asi.cardforge.entity.Kuser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    ModelMapper modelMapper;

    public UserService() {
        this.modelMapper = new ModelMapper();
    }

    public Kuser convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, Kuser.class);
    }

    public UserDTO convertToDTO(Kuser user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
