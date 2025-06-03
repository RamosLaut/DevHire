package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.CompanyLikeDTO;
import TP_Final.devhire.DTOS.DeveloperLikeDTO;
import TP_Final.devhire.Entities.LikeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {
    @Autowired
    private ModelMapper modelMapper;

    public CompanyLikeDTO convertToCompanyLikeDTO(LikeEntity like){
        return modelMapper.map(like, CompanyLikeDTO.class);
    }

    public LikeEntity convertToModelFromCompanyLikeDTO(CompanyLikeDTO companyLikeDTO){
        return modelMapper.map(companyLikeDTO, LikeEntity.class);
    }
    public DeveloperLikeDTO convertToDeveloperLikeDTO(LikeEntity like){
        return modelMapper.map(like, DeveloperLikeDTO.class);
    }

    public LikeEntity convertToModelFromDeveloperLikeDTO(DeveloperLikeDTO developerLikeDTO){
        return modelMapper.map(developerLikeDTO, LikeEntity.class);
    }
}
