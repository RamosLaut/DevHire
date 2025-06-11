package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.ApplicationController;
import TP_Final.devhire.DTOS.ApplicationDTO;
import TP_Final.devhire.Entities.ApplicationEntity;
import TP_Final.devhire.Mappers.ApplicationMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ApplicationAssembler implements RepresentationModelAssembler<ApplicationEntity, EntityModel<ApplicationDTO>> {
    @Autowired
    private ApplicationMapper mapper;

    @Override
    public @NonNull EntityModel<ApplicationDTO> toModel(@NonNull ApplicationEntity entity) {
        ApplicationDTO applicationDTO = mapper.convertToDTO(entity);
        return EntityModel.of(applicationDTO, linkTo(methodOn(ApplicationController.class).findById(entity.getId())).withSelfRel());
    }
    public @NonNull EntityModel<ApplicationDTO> toCompanyModel(@NonNull ApplicationEntity entity, Long jobId) {
        ApplicationDTO applicationDTO = mapper.convertToDTO(entity);
        return EntityModel.of(applicationDTO, linkTo(methodOn(ApplicationController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(ApplicationController.class).discardApplicantByDevId(entity.getDev().getId(), jobId)).withRel("Discard applicant"));
    }
    public @NonNull EntityModel<ApplicationDTO> toDevModel(@NonNull ApplicationEntity entity){
        ApplicationDTO applicationDTO = mapper.convertToDTO(entity);
        return EntityModel.of(applicationDTO, linkTo(methodOn(ApplicationController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(ApplicationController.class).deleteOwnApplication(entity.getId())).withRel("Delete"));
        }
    }
