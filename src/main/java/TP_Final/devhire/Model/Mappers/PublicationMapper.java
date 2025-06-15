package TP_Final.devhire.Model.Mappers;

import TP_Final.devhire.Model.DTOS.PublicationDTO;
import TP_Final.devhire.Model.Entities.PublicationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationMapper {
    @Autowired
    private ModelMapper modelMapper;
    public PublicationDTO converToPublicationDTO(PublicationEntity publication){
        return modelMapper.map(publication, PublicationDTO.class);
    }
    public PublicationEntity convertToPublicationEntity(PublicationDTO publicationDto){
        return modelMapper.map(publicationDto, PublicationEntity.class);
    }
}
