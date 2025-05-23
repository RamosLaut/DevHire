package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.UserController;
import TP_Final.devhire.DTOS.UserDTO;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserEntity, EntityModel<UserDTO>> {
    @Autowired
    UserMapper userMapper;

    @Override
    public EntityModel<UserDTO> toModel(UserEntity entity) {
        UserDTO dto = userMapper.converToDto(entity);
        return EntityModel.of(dto,
                linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).listAllUsers()).withRel("list all users"));
    }


}

