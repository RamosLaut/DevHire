package TP_Final.devhire.Model.Mappers;

import TP_Final.devhire.Model.DTOS.AdminDTO;
import TP_Final.devhire.Model.Entities.AdminEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper{
    @Autowired
    private ModelMapper modelMapper;

    public AdminDTO convertToAdminDTO(AdminEntity admin){
        return modelMapper.map(admin, AdminDTO.class);
    }

    public AdminEntity convertToAdminEntity(AdminDTO adminDTO){
        return modelMapper.map(adminDTO, AdminEntity.class);
    }
}
