package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Entities.JobEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class JobAssembler implements RepresentationModelAssembler<JobEntity, EntityModel<JobEntity>> {
    @Override
    public EntityModel<JobEntity> toModel(JobEntity entity) {
        return null;
    }
}
