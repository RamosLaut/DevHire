package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.PublicationController;
import TP_Final.devhire.Entities.PublicationEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PublicationAssembler implements RepresentationModelAssembler<PublicationEntity, EntityModel<PublicationEntity>> {
    @Override
    public EntityModel<PublicationEntity> toModel(PublicationEntity publication) {
        return EntityModel.of(publication, linkTo(methodOn(PublicationController.class).findAll()).withSelfRel());
    }
}
