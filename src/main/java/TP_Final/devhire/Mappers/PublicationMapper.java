package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.CompanyPublicationDTO;
import TP_Final.devhire.DTOS.DeveloperPublicationDTO;
import TP_Final.devhire.Entities.PublicationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationMapper {
    @Autowired
    private ModelMapper modelMapper;
    public DeveloperPublicationDTO converToUserPublicationDTO(PublicationEntity publication){
        return modelMapper.map(publication, DeveloperPublicationDTO.class);
    }
    public PublicationEntity converFromUserPublicationDTO(DeveloperPublicationDTO developerPublicationDto){
        return modelMapper.map(developerPublicationDto, PublicationEntity.class);
    }
    public CompanyPublicationDTO converToCompanyPublicationDTO(PublicationEntity publication){
        return modelMapper.map(publication, CompanyPublicationDTO.class);
    }
}
