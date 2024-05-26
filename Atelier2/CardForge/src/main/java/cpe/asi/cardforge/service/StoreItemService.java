package cpe.asi.cardforge.service;

import cpe.asi.cardforge.dto.StoreItemDTO;
import cpe.asi.cardforge.entity.StoreItem;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StoreItemService {
    private final ModelMapper modelMapper;

    public StoreItemService() {
        this.modelMapper = new ModelMapper();
    }

    public StoreItemDTO convertToDTO(StoreItem storeItem) {
        return modelMapper.map(storeItem, StoreItemDTO.class);
    }

    public StoreItem convertToEntity(StoreItemDTO storeItemDTO) {
        return modelMapper.map(storeItemDTO, StoreItem.class);
    }
}
