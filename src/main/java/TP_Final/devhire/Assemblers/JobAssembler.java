package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.JobController;
import TP_Final.devhire.DTOS.JobDTO;
import TP_Final.devhire.Entities.JobEntity;
import TP_Final.devhire.Mappers.JobMapper;
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
              linkTo(methodOn(JobController.class).findById(entity.getId())).withSelfRel());
//              linkTo(methodOn(JobController.class).findJobRequirements(entity.getId())).withRel("Requirements"));
//              linkTo(methodOn(JobController.class).(entity.getId())).withRel("delete"),
//              linkTo(methodOn(JobController.class).deleteByCompanyName(entity.getCompany().getName())).withRel("delete by name"),
//              linkTo(methodOn(JobController.class).UpdateJobDescription(jobDTO)).withRel("update"));
//             linkTo(methodOn(JobService.class).getHardSkills()).withRel("Add hard skills"));

    }
}
