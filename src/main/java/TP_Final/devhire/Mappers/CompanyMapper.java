package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.CommentDTO;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Entities.CompanyEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class CompanyMapper {
    @Autowired
    private ModelMapper modelMapper;
    public CompanyDTO convertToDTO(CompanyEntity company){
        return modelMapper.map(company,CompanyDTO.class);
    }
    public CompanyEntity convertToEntity(CompanyDTO companyDTO){
        return modelMapper.map(companyDTO,CompanyEntity.class);
}

}

