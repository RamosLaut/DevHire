package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.LikeController;
import TP_Final.devhire.DTOS.LikeDTO;
import TP_Final.devhire.Entities.LikeEntity;
import TP_Final.devhire.Mappers.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class LikeAssembler implements RepresentationModelAssembler<LikeEntity, EntityModel<LikeDTO>> {
    @Autowired
    LikeMapper likeMapper;
    @Override
    public EntityModel<LikeDTO> toModel(LikeEntity like) {
        LikeDTO likeDTO = likeMapper.convertToDTO(like);
        likeDTO.setDev_id(like.getDeveloper().getId());
        likeDTO.setPublication_id(like.getPublication().getId());
        return EntityModel.of(likeDTO, linkTo(methodOn(LikeController.class).findAll()).withRel("Likes"),
                    linkTo(methodOn(LikeController.class).findById(like.getId())).withSelfRel(),
                    linkTo(methodOn(LikeController.class).deleteById(like.getId())).withRel("Delete like"));
    }
}
