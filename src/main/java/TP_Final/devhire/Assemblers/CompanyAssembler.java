package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.CompanyController;
import TP_Final.devhire.Model.DTOS.CompanyDTO;
import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Mappers.CompanyMapper;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CompanyAssembler implements RepresentationModelAssembler<CompanyEntity, EntityModel<CompanyDTO>> {
    CompanyMapper mapper;

    @Autowired
    public CompanyAssembler(CompanyMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public @NonNull EntityModel<CompanyDTO> toModel(@NonNull CompanyEntity entity) {
        CompanyDTO companyDTO = mapper.convertToDTO(entity);
        return EntityModel.of(companyDTO,
                linkTo(methodOn(CompanyController.class).findById(entity.getId())).withSelfRel());
    }

    public @NonNull EntityModel<CompanyDTO> toOwnCompanyModel(@NonNull CompanyEntity entity) {
        CompanyDTO companyDTO = mapper.convertToDTO(entity);
        return EntityModel.of(companyDTO,
                linkTo(methodOn(CompanyController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(CompanyController.class).deleteOwnCompany()).withRel("Delete"),
                linkTo(methodOn(CompanyController.class).updateOwnCompany(companyDTO)).withRel("Update"));
    }
}
