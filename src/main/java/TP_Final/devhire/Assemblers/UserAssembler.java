package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.PublicationController;
import TP_Final.devhire.Controllers.UserController;
import TP_Final.devhire.DTOS.UserDTO;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Mappers.UserMapper;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserEntity, EntityModel<UserDTO>> {
    @Autowired
    private UserMapper userMapper;

    @Override
    public @NotNull EntityModel<UserDTO> toModel(@NotNull UserEntity user) {
        return null;
    }

//    UserDTO userDTO = userMapper.converToDto(user);
//        return EntityModel.of(userDTO, linkTo(methodOn(UserController.class). );
//}
//
//    public UserEntity toEntity(UserRegisterDTO dto) {
//        return modelMapper.map(dto, UserEntity.class);
//    }
//
//    public UserEntity toEntity(UserUpdateDTO dto) {
//        return modelMapper.map(dto, UserEntity.class);
//    }
//
//    public UserResponseDTO toResponseDTO(UserEntity entity) {
//        return modelMapper.map(entity, UserResponseDTO.class);
//    }
}

