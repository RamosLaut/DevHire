package TP_Final.devhire.Assemblers;

import TP_Final.devhire.Controllers.CommentController;
import TP_Final.devhire.DTOS.CommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Mappers.CommentMapper;
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
    public EntityModel<CommentDTO> toModel(CommentEntity entity) {
        CommentDTO commentDTO = commentMapper.convertToDTO(entity);
        commentDTO.setUser_id(entity.getUser().getUser_id());
        commentDTO.setPublication_id(entity.getPublication().getPublication_id());
        return EntityModel.of(commentDTO, linkTo(methodOn(CommentController.class).findAll()).withRel("Comments"),
                linkTo(methodOn(CommentController.class).findById(entity.getComment_id())).withSelfRel(),
                linkTo(methodOn(CommentController.class).updateContent(entity)).withRel("Update content"),
                linkTo(methodOn(CommentController.class).deleteById(entity.getComment_id())).withRel("Delete"));
    }
}
