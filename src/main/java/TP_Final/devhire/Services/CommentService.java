package TP_Final.devhire.Services;

import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
