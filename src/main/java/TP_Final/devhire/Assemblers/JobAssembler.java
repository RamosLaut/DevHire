package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.ApplicationController;
import TP_Final.devhire.Controllers.JobController;
import TP_Final.devhire.Model.DTOS.JobDTO;
import TP_Final.devhire.Model.Entities.JobEntity;
import TP_Final.devhire.Model.Mappers.JobMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JobAssembler implements RepresentationModelAssembler<JobEntity, EntityModel<JobDTO>> {
    @Autowired
    JobMapper mapper;
    @Override
    public @NonNull EntityModel<JobDTO> toModel(@NonNull JobEntity entity) {
        JobDTO jobDTO = mapper.convertToDTO(entity);
        return EntityModel.of(jobDTO,
                linkTo(methodOn(JobController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(JobController.class).deleteJobOffer(entity.getId())).withRel("Delete"),
                linkTo(methodOn(JobController.class).findJobOfferRequirements(entity.getId())).withRel("Requirements"),
                linkTo(methodOn(JobController.class).findAvailableSkills(entity.getId())).withRel("Skills to add"),
                linkTo(methodOn(ApplicationController.class).getApplicantsByJobId(entity.getId())).withRel("See candidates"));
    }
    public @NonNull EntityModel<JobDTO> toDevModel(@NonNull JobEntity entity) {
        JobDTO jobDTO = mapper.convertToDTO(entity);
        return EntityModel.of(jobDTO,
                linkTo(methodOn(JobController.class).apply(entity.getId())).withRel("Apply to this job"),
                linkTo(methodOn(JobController.class).findJobOfferRequirements(entity.getId())).withRel("See job requirements"));
    }
}
