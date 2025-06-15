package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.ApplicationController;
import TP_Final.devhire.Controllers.DeveloperController;
import TP_Final.devhire.Model.DTOS.DeveloperApplicantDTO;
import TP_Final.devhire.Model.DTOS.DeveloperDTO;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Model.Mappers.DeveloperMapper;
import lombok.NonNull;
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
    public @NonNull EntityModel<DeveloperDTO> toModel(@NonNull DeveloperEntity entity) {
        DeveloperDTO dto = developerMapper.convertToDto(entity);
        dto.setEmail(entity.getCredentials().getEmail());
        return EntityModel.of(dto,
                linkTo(methodOn(DeveloperController.class).getDevById(entity.getId())).withSelfRel(),
                linkTo(methodOn(DeveloperController.class).updateDev(entity.getId())).withRel("Update"),
                linkTo(methodOn(DeveloperController.class).logicDown(entity.getId())).withRel("Deactivate account"));
    }
    public EntityModel<DeveloperApplicantDTO> toModelApplication(DeveloperEntity entity, Long jobId) {
        DeveloperApplicantDTO applicantDTO = developerMapper.convertToApplicantDTO(entity);
        applicantDTO.setEmail(entity.getCredentials().getEmail());
        return EntityModel.of(applicantDTO,
                linkTo(methodOn(DeveloperController.class).getDevById(entity.getId())).withRel("View profile"),
                linkTo(methodOn(ApplicationController.class).discardApplicantByDevId(entity.getId(), jobId)).withRel("Discard application"));
    }
}

