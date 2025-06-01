package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.PublicationController;
import TP_Final.devhire.DTOS.CompanyPublicationDTO;
import TP_Final.devhire.DTOS.DeveloperPublicationDTO;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Mappers.PublicationMapper;
import io.micrometer.common.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PublicationAssembler implements RepresentationModelAssembler<PublicationEntity, EntityModel<Object>> {
    @Autowired
    PublicationMapper mapper;
    @Override
    public @NonNull EntityModel<Object> toModel(@NonNull PublicationEntity publication) {
        if(publication.getCompany() != null){
            CompanyPublicationDTO companyPublication = mapper.converToCompanyPublicationDTO(publication);
            companyPublication.setCompanyName(publication.getCompany().getName());
            return EntityModel.of(companyPublication, linkTo(methodOn(PublicationController.class).findById(companyPublication.getId())).withSelfRel(),
                    linkTo(methodOn(PublicationController.class).deleteById(companyPublication.getId())).withRel("Delete publication"),
                    linkTo(methodOn(PublicationController.class).updateContent(publication)).withRel("Update content"));
        }else{
            DeveloperPublicationDTO developerPublicationDTO = mapper.converToUserPublicationDTO(publication);
            developerPublicationDTO.setUserName(publication.getDeveloper().getName());
            return EntityModel.of(developerPublicationDTO, linkTo(methodOn(PublicationController.class).findById(developerPublicationDTO.getId())).withSelfRel(),
                    linkTo(methodOn(PublicationController.class).deleteById(developerPublicationDTO.getId())).withRel("Delete publication"),
                    linkTo(methodOn(PublicationController.class).updateContent(publication)).withRel("Update content"));
        }
    }

}
