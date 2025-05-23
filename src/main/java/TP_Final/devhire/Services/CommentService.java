package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.CommentAssembler;
import TP_Final.devhire.DTOS.CommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Exceptions.CommentNotFoundException;
import TP_Final.devhire.Exceptions.PublicationNotFoundException;
import TP_Final.devhire.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentAssembler assembler;
    @Autowired
    public CommentService(CommentRepository commentRepository, CommentAssembler assembler) {
        this.commentRepository = commentRepository;
        this.assembler = assembler;
    }
    public void save(CommentEntity comment){
        commentRepository.save(comment);
    }
    public CollectionModel<EntityModel<CommentDTO>> findAll(){
        List<EntityModel<CommentDTO>>comments = commentRepository.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(comments);
    }
    public EntityModel<CommentDTO>findById(Long commentId)throws CommentNotFoundException {

        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        return assembler.toModel(comment.orElseThrow(()->new CommentNotFoundException("Comment not found")));
    }
    public EntityModel<CommentDTO> updateContent(CommentEntity comment){
        commentRepository.updateContent(comment.getContent(), comment.getId());
        return assembler.toModel(comment);
    }
    public void deleteById(Long commentId){
        commentRepository.deleteById(commentId);
    }
    public CollectionModel<EntityModel<CommentDTO>> findByPublicationId(@PathVariable long publicationId)throws PublicationNotFoundException {
        List<EntityModel<CommentDTO>> comments = commentRepository.findByPublicationId(publicationId).orElseThrow(()->new PublicationNotFoundException("Publication not found"))
                .stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(comments);
    }
}
