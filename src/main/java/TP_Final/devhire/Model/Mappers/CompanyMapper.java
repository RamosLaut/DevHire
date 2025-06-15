package TP_Final.devhire.Model.Mappers;

import TP_Final.devhire.Model.DTOS.CompanyDTO;
import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.CompanyEntity;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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

