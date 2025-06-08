package TP_Final.devhire.Services;
import TP_Final.devhire.Assemblers.CommentAssembler;
import TP_Final.devhire.DTOS.CommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Exceptions.*;
import TP_Final.devhire.Repositories.CommentRepository;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import TP_Final.devhire.Repositories.PublicationsRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


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
    public EntityModel<CommentDTO> save(CommentEntity comment, @NonNull Long publicationId)throws NotFoundException, ContentRequiredException, CredentialsRequiredException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent())
        {
            comment.setCompany(companyRepository.findByCredentials_Email(email).get());
        }else if(developerRepository.findByCredentials_Email(email).isPresent()){
            comment.setDeveloper(developerRepository.findByCredentials_Email(email).get());
        }else throw new CredentialsRequiredException("Credentials required");
        Optional<PublicationEntity> publicationOpt = publicationsRepository.findById(publicationId);
        if(publicationOpt.isEmpty()){
            throw new NotFoundException("Publication not found");
        }
        if(comment.getContent()==null){
            throw new ContentRequiredException("Comment content required");
        }
        comment.setPublication(publicationOpt.get());
        commentRepository.save(comment);
        return assembler.toModel(comment);
    }
    public CollectionModel<EntityModel<CommentDTO>> findAll(){
        return CollectionModel.of(commentRepository.findAll().stream()
                .map(assembler::toModel)
                .toList());
    }
    public CollectionModel<EntityModel<CommentDTO>> findOwnComments(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            return CollectionModel.of(companyRepository.findByCredentials_Email(email).get()
                    .getComments()
                    .stream()
                    .map(assembler::toModel)
                    .toList());
        } else if (developerRepository.findByCredentials_Email(email).isPresent()){
            return CollectionModel.of(developerRepository.findByCredentials_Email(email).get()
                    .getComments()
                    .stream()
                    .map(assembler::toModel)
                    .toList());
        }else {
            throw new RuntimeException("Credentials required");
        }
    }
    public EntityModel<CommentDTO>findById(Long commentId)throws NotFoundException {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        return assembler.toModel(comment.orElseThrow(()->new NotFoundException("Comment not found")));
    }
    @Transactional
    public EntityModel<CommentDTO> updateContent(CommentEntity comment)throws IdRequiredException, NotFoundException, UnauthorizedException, ContentRequiredException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(comment.getId()==null){
            throw new IdRequiredException("Comment ID required");
        }
        if(comment.getContent()==null){
            throw new ContentRequiredException("Comment content required");
        }
        CommentEntity commentEntity = commentRepository.findById(comment.getId())
                .orElseThrow(()->new NotFoundException("Comment not found"));
        Optional<CompanyEntity> companyOpt = companyRepository.findByCredentials_Email(email);
        Optional<DeveloperEntity> devOpt = developerRepository.findByCredentials_Email(email);
        if (companyOpt.isPresent() && companyOpt.get().getComments().contains(commentEntity)) {
            commentEntity.setContent(comment.getContent());
            commentRepository.updateContent(comment.getContent(), comment.getId());
            return assembler.toModel(commentEntity);
        }
        if (devOpt.isPresent() && devOpt.get().getComments().contains(commentEntity)) {
            commentEntity.setContent(comment.getContent());
            commentRepository.updateContent(comment.getContent(), comment.getId());
            return assembler.toModel(commentEntity);
        }
        throw new UnauthorizedException("You are not authorized to update this comment");
    }
    public void delete(Long commentId)throws NotFoundException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(commentRepository.findById(commentId).isEmpty()){
            throw new NotFoundException("Comment not found");
        }
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        if(companyRepository.findByCredentials_Email(email).isPresent() &&
                commentEntity.getCompany().equals(companyRepository.findByCredentials_Email(email).get())){
            commentRepository.deleteById(commentId);
        } else if (developerRepository.findByCredentials_Email(email).isPresent() &&
                    commentEntity.getDeveloper().equals(developerRepository.findByCredentials_Email(email).get())){
            commentRepository.deleteById(commentId);
        }else throw new UnauthorizedException("You don't have permission to delete this comment");
    }
    public CollectionModel<EntityModel<CommentDTO>> findByPublicationId(Long publicationId)throws NotFoundException {
        return CollectionModel.of(commentRepository.findByPublicationId(publicationId).orElseThrow(()->new NotFoundException("Publication not found"))
                .stream()
                .map(assembler::toModel)
                .toList());
    }
}
