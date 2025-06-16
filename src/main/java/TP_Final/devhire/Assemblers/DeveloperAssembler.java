package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.ApplicationController;
import TP_Final.devhire.Controllers.DeveloperController;
import TP_Final.devhire.Model.DTOS.DeveloperApplicantDTO;
import TP_Final.devhire.Model.DTOS.DeveloperDTO;
import TP_Final.devhire.Model.Entities.ApplicationEntity;
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
                linkTo(methodOn(DeveloperController.class).updateDev(entity.getId(), null)).withRel("Update"),
                linkTo(methodOn(DeveloperController.class).deleteDev(entity.getId())).withRel("Delete"));
    }
    public EntityModel<DeveloperApplicantDTO> toDevApplicantModel(DeveloperEntity entity, Long jobId) {
        DeveloperApplicantDTO applicantDTO = developerMapper.convertToApplicantDTO(entity);
        applicantDTO.setEmail(entity.getCredentials().getEmail());
        ApplicationEntity application = entity.getPostulatedJobs().stream().filter(job -> job.getId().equals(jobId)).findFirst().orElse(null);
        return EntityModel.of(applicantDTO,
                linkTo(methodOn(DeveloperController.class).getDevById(entity.getId())).withRel("View profile"),
                linkTo(methodOn(ApplicationController.class).acceptApplication(application.getId())).withRel("Accept application"),
                linkTo(methodOn(ApplicationController.class).rejectApplication(application.getId())).withRel("Reject application"));
    }
    public EntityModel<DeveloperApplicantDTO> onlySelfRel(DeveloperEntity entity) {
        DeveloperApplicantDTO applicantDTO = developerMapper.convertToApplicantDTO(entity);
        applicantDTO.setEmail(entity.getCredentials().getEmail());
        return EntityModel.of(applicantDTO,
                linkTo(methodOn(DeveloperController.class).getDevById(entity.getId())).withSelfRel());
    }
}

