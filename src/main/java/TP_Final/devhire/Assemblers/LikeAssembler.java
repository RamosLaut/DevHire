package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.LikeController;
import TP_Final.devhire.DTOS.LikeDTO;
import TP_Final.devhire.Entities.LikeEntity;
import TP_Final.devhire.Mappers.LikeMapper;
import lombok.NonNull;
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
    public @NonNull EntityModel<LikeDTO> toModel(LikeEntity like) {
        LikeDTO likeDTO = likeMapper.convertToLikeDTO(like);
        if(like.getCompany()!=null){
            likeDTO.setName(like.getCompany().getName());

        }
        if(like.getDeveloper()!=null){
            likeDTO.setName(like.getDeveloper().getName());
        }
        return EntityModel.of(likeDTO, linkTo(methodOn(LikeController.class).findById(likeDTO.getId())).withSelfRel());
    }
    public @NonNull EntityModel<LikeDTO> toOwnLikeModel(@NonNull LikeEntity like){
        LikeDTO likeDTO = likeMapper.convertToLikeDTO(like);
        if(like.getCompany()!=null){
            likeDTO.setName(like.getCompany().getName());
        }
        if(like.getDeveloper()!=null){
            likeDTO.setName(like.getDeveloper().getName());
        }
        return EntityModel.of(likeDTO, linkTo(methodOn(LikeController.class).findById(likeDTO.getId())).withSelfRel(),
                linkTo(methodOn(LikeController.class).deleteById(like.getId())).withRel("Unlike"));

    }
}
