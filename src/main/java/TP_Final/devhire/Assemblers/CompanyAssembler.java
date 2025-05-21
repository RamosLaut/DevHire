package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.CompanyController;
import TP_Final.devhire.DTOS.CompanyDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Mappers.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CompanyAssembler implements RepresentationModelAssembler<CompanyEntity, EntityModel<CompanyDTO>> {
    @Autowired
   private CompanyMapper mapper;
    @Override
    public EntityModel<CompanyDTO> toModel(CompanyEntity entity) {
        CompanyDTO companyDTO = mapper.convertToDTO(entity);
            return EntityModel.of(companyDTO,
                    linkTo(methodOn(CompanyController.class).findAll()).withRel("companies"),
                    linkTo(methodOn(CompanyController.class).findById(entity.getCompany_id())).withSelfRel(),
                    linkTo(methodOn(CompanyController.class).deleteById(entity.getCompany_id())).withRel("Delete"),
                    linkTo(methodOn(CompanyController.class).updateCompany(entity)).withRel("Update"));
        }
}
