package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Entities.CompanyEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CompanyAssembler implements RepresentationModelAssembler<CompanyEntity, EntityModel<CompanyEntity>> {
    @Override
    public EntityModel<CompanyEntity> toModel(CompanyEntity entity) {
        return null;
    }
}
