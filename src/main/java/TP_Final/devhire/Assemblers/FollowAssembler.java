package TP_Final.devhire.Assemblers;

import TP_Final.devhire.DTOS.FollowResponseDTO;
import TP_Final.devhire.Mappers.FollowMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class FollowAssembler implements RepresentationModelAssembler<Object, EntityModel<FollowResponseDTO>> {

    private final FollowMapper followMapper;

    public FollowAssembler(FollowMapper followMapper) {
        this.followMapper = followMapper;
    }

    @Override
    public EntityModel<FollowResponseDTO> toModel(Object entity) {
        FollowResponseDTO dto = followMapper.toDTO(entity);
        EntityModel<FollowResponseDTO> model = EntityModel.of(dto);
        return null;
    }

    @Override
    public CollectionModel<EntityModel<FollowResponseDTO>> toCollectionModel(Iterable<?> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
