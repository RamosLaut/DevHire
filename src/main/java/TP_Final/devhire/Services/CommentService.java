package TP_Final.devhire.Services;
import TP_Final.devhire.Assemblers.CommentAssembler;
import TP_Final.devhire.DTOS.CommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Exceptions.*;
import TP_Final.devhire.Mappers.CommentMapper;
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


import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentAssembler assembler;
    private final CompanyRepository companyRepository;
    private final DeveloperRepository developerRepository;
    private final PublicationsRepository publicationsRepository;
    private final CommentMapper mapper;
    @Autowired
    public CommentService(CommentRepository commentRepository, CommentAssembler assembler, CompanyRepository companyRepository, DeveloperRepository developerRepository, PublicationsRepository publicationsRepository, CommentMapper mapper) {
        this.commentRepository = commentRepository;
        this.assembler = assembler;
        this.companyRepository = companyRepository;
        this.developerRepository = developerRepository;
        this.publicationsRepository = publicationsRepository;
        this.mapper = mapper;
    }
    public EntityModel<CommentDTO> save(CommentDTO commentDTO, @NonNull Long publicationId)throws NotFoundException, ContentRequiredException, CredentialsRequiredException{
        CommentEntity comment = mapper.convertToEntity(commentDTO);
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
        comment.setPublication(publicationOpt.get());
        commentRepository.save(comment);
        return assembler.toOwnCommentModel(comment);
    }
    public CollectionModel<EntityModel<CommentDTO>> findAll(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            List<EntityModel<CommentDTO>> ownComments = commentRepository.findAll().stream()
                    .filter(comment -> company.equals(comment.getCompany()))
                    .map(assembler::toOwnCommentModel)
                    .toList();
            List<EntityModel<CommentDTO>> otherComments = commentRepository.findAll().stream()
                    .filter(comment -> !company.equals(comment.getCompany()))
                    .map(assembler::toModel)
                    .toList();
            List<EntityModel<CommentDTO>> allComments = Stream.concat(otherComments.stream(), ownComments.stream()).toList();
            if (allComments.isEmpty()) {
                throw new RuntimeException("No comments found");
            }
            return CollectionModel.of(allComments);
        }else if(developerRepository.findByCredentials_Email(email).isPresent()){
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            List<EntityModel<CommentDTO>> ownComments = commentRepository.findAll().stream()
                    .filter(comment -> developer.equals(comment.getDeveloper()))
                    .map(assembler::toOwnCommentModel)
                    .toList();
            List<EntityModel<CommentDTO>> otherComments = commentRepository.findAll().stream()
                    .filter(comment -> developer.equals(comment.getDeveloper()))
                    .map(assembler::toModel)
                    .toList();
            List<EntityModel<CommentDTO>> allComments = Stream.concat(ownComments.stream(), otherComments.stream()).toList();
            if (allComments.isEmpty()) {
                throw new RuntimeException("No comments found");
            }
            return CollectionModel.of(allComments);
        } else throw new CredentialsRequiredException("You need to be logged in to see comments list");
    }
    public CollectionModel<EntityModel<CommentDTO>> findOwnComments(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            List<EntityModel<CommentDTO>> comments = company.getComments()
                    .stream()
                    .map(assembler::toOwnCommentModel)
                    .toList();
            if (comments.isEmpty()) {
                throw new NotFoundException("No comments found");
            }
            return CollectionModel.of(comments);
        } else if (developerRepository.findByCredentials_Email(email).isPresent()){
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            List<EntityModel<CommentDTO>> comments = developer.getComments().stream()
                    .map(assembler::toOwnCommentModel)
                    .toList();
            if (comments.isEmpty()) {
                throw new NotFoundException("No comments found");
            }
            return CollectionModel.of(comments);
        }else {
            throw new CredentialsRequiredException("You need to be logged in to see your comments");
        }
    }
    public EntityModel<CommentDTO>findById(Long commentId)throws NotFoundException {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()){
            throw new NotFoundException("Comment not found");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(developerRepository.findByCredentials_Email(email).isPresent()){
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            if(developer.getComments().contains(comment.get())){
                return assembler.toOwnCommentModel(comment.get());
            }else return assembler.toModel(comment.get());
        }else if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            if(company.getComments().contains(comment.get())){
                return assembler.toOwnCommentModel(comment.get());
            }else return assembler.toModel(comment.get());
        }else throw new CredentialsRequiredException("You need to be logged in to see comments");
    }
    @Transactional
    public EntityModel<CommentDTO> updateContent(CommentDTO commentDTO)throws IdRequiredException, NotFoundException, UnauthorizedException, ContentRequiredException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(commentDTO.getId()==null){
            throw new IdRequiredException("Comment ID required");
        }
        CommentEntity commentEntity = commentRepository.findById(commentDTO.getId())
                .orElseThrow(()->new NotFoundException("Comment not found"));
        Optional<CompanyEntity> companyOpt = companyRepository.findByCredentials_Email(email);
        Optional<DeveloperEntity> devOpt = developerRepository.findByCredentials_Email(email);
        if (companyOpt.isPresent() && companyOpt.get().getComments().contains(commentEntity)) {
            commentEntity.setContent(commentDTO.getContent());
            commentRepository.updateContent(commentDTO.getContent(), commentDTO.getId());
            return assembler.toModel(commentEntity);
        }
        if (devOpt.isPresent() && devOpt.get().getComments().contains(commentEntity)) {
            commentEntity.setContent(commentDTO.getContent());
            commentRepository.updateContent(commentDTO.getContent(), commentDTO.getId());
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
        List<CommentEntity> comments = commentRepository.findByPublicationId(publicationId).orElseThrow(()->new NotFoundException("Publication not found"));
        if(comments.isEmpty()){
            throw new NotFoundException("No comments found");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            List<EntityModel<CommentDTO>> ownComments = commentRepository.findAll().stream()
                    .filter(comment -> company.equals(comment.getCompany()))
                    .map(assembler::toOwnCommentModel)
                    .toList();
            List<EntityModel<CommentDTO>> otherComments = commentRepository.findAll().stream()
                    .filter(comment -> !company.equals(comment.getCompany()))
                    .map(assembler::toModel)
                    .toList();
            List<EntityModel<CommentDTO>> allComments = Stream.concat(otherComments.stream(), ownComments.stream()).toList();
            if (allComments.isEmpty()) {
                throw new NotFoundException("No comments found");
            }
            return CollectionModel.of(allComments);
        }else if(developerRepository.findByCredentials_Email(email).isPresent()){
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            List<EntityModel<CommentDTO>> ownComments = commentRepository.findAll().stream()
                    .filter(comment -> developer.equals(comment.getDeveloper()))
                    .map(assembler::toOwnCommentModel)
                    .toList();
            List<EntityModel<CommentDTO>> otherComments = commentRepository.findAll().stream()
                    .filter(comment -> !developer.equals(comment.getDeveloper()))
                    .map(assembler::toModel)
                    .toList();
            List<EntityModel<CommentDTO>> allComments = Stream.concat(otherComments.stream(), ownComments.stream()).toList();
            if (allComments.isEmpty()) {
                throw new NotFoundException("No comments found");
            }
            return CollectionModel.of(allComments);
        } else throw new CredentialsRequiredException("You need to be logged in to see comments");
    }
}
