package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Entities.UserEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserEntity, EntityModel<UserEntity>> {
    @Override
    public EntityModel<UserEntity> toModel(UserEntity entity) {
        return null;
    }
}
