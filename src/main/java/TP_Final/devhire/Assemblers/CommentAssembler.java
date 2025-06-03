package TP_Final.devhire.Assemblers;
import TP_Final.devhire.Controllers.CommentController;
import TP_Final.devhire.DTOS.CompanyCommentDTO;
import TP_Final.devhire.DTOS.DevCommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Mappers.CommentMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentAssembler implements RepresentationModelAssembler<CommentEntity, EntityModel<Object>> {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public @NonNull EntityModel<Object> toModel(CommentEntity entity) {
        if (entity.getCompany() != null) {
            CompanyCommentDTO companyComment = commentMapper.convertToCompanyCommentDTO(entity);
            return EntityModel.of(companyComment, linkTo(methodOn(CommentController.class).findById(entity.getId())).withSelfRel(),
                    linkTo(methodOn(CommentController.class).updateContent(entity)).withRel("Update content"),
                    linkTo(methodOn(CommentController.class).deleteById(entity.getId())).withRel("Delete"));
        } else if (entity.getDeveloper() != null){
            DevCommentDTO devCommentDTO = commentMapper.convertToDevCommentDTO(entity);
            return EntityModel.of(devCommentDTO, linkTo(methodOn(CommentController.class).findById(entity.getId())).withSelfRel(),
                    linkTo(methodOn(CommentController.class).updateContent(entity)).withRel("Update content"),
                    linkTo(methodOn(CommentController.class).deleteById(entity.getId())).withRel("Delete"));
        }else throw new IllegalStateException("Comment is not associated with a developer or a company");
    }
}

