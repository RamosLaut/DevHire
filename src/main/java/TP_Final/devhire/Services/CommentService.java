package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.CommentAssembler;
import TP_Final.devhire.DTOS.DevCommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Exceptions.CommentNotFoundException;
import TP_Final.devhire.Exceptions.PublicationNotFoundException;
import TP_Final.devhire.Repositories.CommentRepository;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import TP_Final.devhire.Repositories.PublicationsRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentAssembler assembler;
    private final CompanyRepository companyRepository;
    private final DeveloperRepository developerRepository;
    private final PublicationsRepository publicationsRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository, CommentAssembler assembler, CompanyRepository companyRepository, DeveloperRepository developerRepository, PublicationsRepository publicationsRepository) {
        this.commentRepository = commentRepository;
        this.assembler = assembler;
        this.companyRepository = companyRepository;
        this.developerRepository = developerRepository;
        this.publicationsRepository = publicationsRepository;
    }
    public EntityModel<Object> save(CommentEntity comment, @NonNull Long publicationId)throws PublicationNotFoundException, RuntimeException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CompanyEntity> companyOpt = companyRepository.findByCredentials_Email(email);
        Optional<DeveloperEntity> devOpt = developerRepository.findByCredentials_Email(email);
        Optional<PublicationEntity> publicationOpt = publicationsRepository.findById(publicationId);
        if(publicationOpt.isEmpty()){
            throw new PublicationNotFoundException("Publication not found");
        }
        if(comment.getContent()==null){
            throw new RuntimeException("Comment content required");
        }
        comment.setPublication(publicationOpt.get());
        companyOpt.ifPresent(comment::setCompany);
        devOpt.ifPresent(comment::setDeveloper);
        commentRepository.save(comment);
        return assembler.toModel(comment);
    }
    public CollectionModel<EntityModel<Object>> findAll(){
        return CollectionModel.of(commentRepository.findAll().stream()
                .map(assembler::toModel)
                .toList());
    }
    public EntityModel<Object>findById(Long commentId)throws CommentNotFoundException {

        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        return assembler.toModel(comment.orElseThrow(()->new CommentNotFoundException("Comment not found")));
    }
    public EntityModel<Object> updateContent(CommentEntity comment){
        commentRepository.updateContent(comment.getContent(), comment.getId());
        return assembler.toModel(comment);
    }
    public void deleteById(Long commentId){
        commentRepository.deleteById(commentId);
    }
    public CollectionModel<EntityModel<Object>> findByPublicationId(@PathVariable long publicationId)throws PublicationNotFoundException {
        return CollectionModel.of(commentRepository.findByPublicationId(publicationId).orElseThrow(()->new PublicationNotFoundException("Publication not found"))
                .stream()
                .map(assembler::toModel)
                .toList());
    }
}
