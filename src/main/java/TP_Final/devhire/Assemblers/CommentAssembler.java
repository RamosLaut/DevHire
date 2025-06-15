package TP_Final.devhire.Assemblers;
import TP_Final.devhire.Controllers.CommentController;
import TP_Final.devhire.Model.DTOS.CommentDTO;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.CommentEntity;
import TP_Final.devhire.Model.Mappers.CommentMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentAssembler implements RepresentationModelAssembler<CommentEntity, EntityModel<CommentDTO>> {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public @NonNull EntityModel<CommentDTO> toModel(@NonNull CommentEntity entity) {
        CommentDTO commentDTO = commentMapper.convertCommentDTO(entity);
        if (entity.getCompany() != null) {
            commentDTO.setName(entity.getCompany().getName());
        }
        if(entity.getDeveloper()!=null){
            commentDTO.setName(entity.getDeveloper().getName());
        }
        return EntityModel.of(commentDTO, linkTo(methodOn(CommentController.class).findById(entity.getId())).withSelfRel());
    }
    public @NonNull EntityModel<CommentDTO> toOwnCommentModel(CommentEntity entity) {
        CommentDTO commentDTO = commentMapper.convertCommentDTO(entity);
        if (entity.getCompany() != null) {
            commentDTO.setName(entity.getCompany().getName());
        }
        if(entity.getDeveloper()!=null){
            commentDTO.setName(entity.getDeveloper().getName());
        }
        return EntityModel.of(commentDTO, linkTo(methodOn(CommentController.class).findById(entity.getId())).withSelfRel(),
                linkTo(methodOn(CommentController.class).updateContent(commentDTO)).withRel("Update content"),
                linkTo(methodOn(CommentController.class).deleteById(entity.getId())).withRel("Delete"));
    }
}

