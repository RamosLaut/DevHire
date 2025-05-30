package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.CompanyPublicationDTO;
import TP_Final.devhire.DTOS.UserPublicationDTO;
import TP_Final.devhire.Entities.PublicationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationMapper {
    @Autowired
    private ModelMapper modelMapper;
    public UserPublicationDTO converToUserPublicationDTO(PublicationEntity publication){
        return modelMapper.map(publication, UserPublicationDTO.class);
    }
    public PublicationEntity converFromUserPublicationDTO(UserPublicationDTO userPublicationDto){
        return modelMapper.map(userPublicationDto, PublicationEntity.class);
    }
    public CompanyPublicationDTO converToCompanyPublicationDTO(PublicationEntity publication){
        return modelMapper.map(publication, CompanyPublicationDTO.class);
    }
}
