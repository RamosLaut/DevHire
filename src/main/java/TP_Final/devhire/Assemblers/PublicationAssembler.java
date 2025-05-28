package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.PublicationController;
import TP_Final.devhire.DTOS.PublicationDTO;
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
public class PublicationAssembler implements RepresentationModelAssembler<PublicationEntity, EntityModel<PublicationDTO>> {
    @Autowired
    PublicationMapper mapper;
    @Override
    public @NonNull EntityModel<PublicationDTO> toModel(@NonNull PublicationEntity publication) {
        PublicationDTO publicationDTO = mapper.converToDto(publication);
        if(publication.getCompany() != null){
            publicationDTO.setCompany_id(publication.getCompany().getId());
        }else{
            publicationDTO.setUser_id(publication.getUser().getId());
        }
        return EntityModel.of(publicationDTO, linkTo(methodOn(PublicationController.class).findById(publicationDTO.getId())).withSelfRel(),
                linkTo(methodOn(PublicationController.class).deleteById(publicationDTO.getId())).withRel("Delete publication"),
                linkTo(methodOn(PublicationController.class).updateContent(publication)).withRel("Update content"));
    }

}
