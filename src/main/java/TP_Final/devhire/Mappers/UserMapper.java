package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.UserDTO;
import TP_Final.devhire.Entities.UserEntity;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapper{
    @Autowired
    private ModelMapper modelMapper;
    public UserDTO converToDto(UserEntity user){
        return modelMapper.map(user, UserDTO.class);
    }
    public UserEntity converToEntity(UserDTO userDto){
        return modelMapper.map(userDto, UserEntity.class);
    }
}
