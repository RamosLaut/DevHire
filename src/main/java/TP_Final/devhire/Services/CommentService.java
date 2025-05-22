package TP_Final.devhire.Services;

import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Exceptions.CommentNotFoundException;
import TP_Final.devhire.Exceptions.PublicationNotFoundException;
import TP_Final.devhire.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    public List<CommentEntity> findAll(){
        return commentRepository.findAll();
    }
    public void save(CommentEntity comment){
        commentRepository.save(comment);
    }
    public CommentEntity findById(Long commentId)throws CommentNotFoundException {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        return comment.orElseThrow(()->new CommentNotFoundException("Comment not found"));
    }
    public void updateContent(CommentEntity comment){
        commentRepository.updateContent(comment.getContent(), comment.getComment_id());
    }
    public void deleteById(Long commentId){
        commentRepository.deleteById(commentId);
    }
    public List<CommentEntity> findByPublicationId(@PathVariable long publicationId)throws PublicationNotFoundException {
        return commentRepository.findByPublicationId(publicationId).orElseThrow(()->new PublicationNotFoundException("Publication not found"));
    }
}
