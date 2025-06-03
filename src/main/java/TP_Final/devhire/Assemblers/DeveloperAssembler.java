package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.DeveloperController;
import TP_Final.devhire.DTOS.DeveloperDTO;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Mappers.DeveloperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DeveloperAssembler implements RepresentationModelAssembler<DeveloperEntity, EntityModel<DeveloperDTO>> {
    @Autowired
    DeveloperMapper developerMapper;

    @Override
    public EntityModel<DeveloperDTO> toModel(DeveloperEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("DeveloperEntity can't be null");
        }
        DeveloperDTO dto = developerMapper.convertToDto(entity);
        return EntityModel.of(dto,
                linkTo(methodOn(DeveloperController.class).getDevById(entity.getId())).withSelfRel(),
                linkTo(methodOn(DeveloperController.class).updateDev(entity.getId(), null)).withRel("update"),
                linkTo(methodOn(DeveloperController.class).deleteDev(entity.getId())).withRel("delete"));
    }

}

