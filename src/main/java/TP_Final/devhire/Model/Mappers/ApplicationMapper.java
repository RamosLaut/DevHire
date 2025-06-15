package TP_Final.devhire.Model.Mappers;

import TP_Final.devhire.Model.DTOS.ApplicationDTO;
import TP_Final.devhire.Model.Entities.ApplicationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {
    @Autowired
    private ModelMapper mapper;

    public ApplicationDTO convertToDTO(ApplicationEntity application){
        return mapper.map(application,ApplicationDTO.class);
    }
    public ApplicationEntity convertToEntity(ApplicationDTO applicationDTO){
        return mapper.map(applicationDTO,ApplicationEntity.class);
    }
}
