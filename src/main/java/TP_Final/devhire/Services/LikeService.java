package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.LikeAssembler;
import TP_Final.devhire.DTOS.LikeDTO;
import TP_Final.devhire.Entities.LikeEntity;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Exceptions.AlreadyExistsException;
import TP_Final.devhire.Exceptions.CredentialsRequiredException;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.DeveloperRepository;
import TP_Final.devhire.Repositories.LikeRepository;
import TP_Final.devhire.Repositories.PublicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeAssembler assembler;
    private final PublicationsRepository publicationsRepository;
    private final DeveloperRepository developerRepository;
    private final CompanyRepository companyRepository;
    @Autowired
    public LikeService(LikeRepository likeRepository, LikeAssembler assembler, PublicationsRepository publicationsRepository, DeveloperRepository developerRepository, CompanyRepository companyRepository) {
        this.likeRepository = likeRepository;
        this.assembler = assembler;
        this.publicationsRepository = publicationsRepository;
        this.developerRepository = developerRepository;
        this.companyRepository = companyRepository;
    }
    public EntityModel<LikeDTO> save(Long publicationId)throws NotFoundException, CredentialsRequiredException, AlreadyExistsException {
        LikeEntity like = new LikeEntity();
        if(publicationsRepository.findById(publicationId).isEmpty()){
            throw new NotFoundException("Publication not found");
        }
        PublicationEntity publication = publicationsRepository.findById(publicationId).get();
        if(publication.getLikes().contains(like)){
            throw new AlreadyExistsException("Like already exists");
        }
        like.setPublication(publicationsRepository.findById(publicationId).get());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            like.setCompany(companyRepository.findByCredentials_Email(email).get());
        } else if (developerRepository.findByCredentials_Email(email).isPresent()) {
            like.setDeveloper(developerRepository.findByCredentials_Email(email).get());
        }else throw new CredentialsRequiredException("Credentials required");
        likeRepository.save(like);
        return assembler.toModel(like);
    }
    public CollectionModel<EntityModel<LikeDTO>> findAll(){
        List<EntityModel<LikeDTO>> likes = likeRepository.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(likes);
    }
    public CollectionModel<EntityModel<LikeDTO>> findOwnLikes(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            return CollectionModel.of(companyRepository.findByCredentials_Email(email).get()
                    .getLikes()
                    .stream()
                    .map(assembler::toModel)
                    .toList());
        }else if(developerRepository.findByCredentials_Email(email).isPresent()){
            return CollectionModel.of(developerRepository.findByCredentials_Email(email).get()
                    .getLikes()
                    .stream()
                    .map(assembler::toModel)
                    .toList());
        }else throw new CredentialsRequiredException("Credentials required");
    }
    public EntityModel<LikeDTO> findById(long id)throws NotFoundException, CredentialsRequiredException{
        return assembler.toModel(likeRepository.findById(id).orElseThrow(()->new NotFoundException("Like not found")));
    }
    public void deleteById(long id)throws NotFoundException {
        if(likeRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Like not found");
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()
                && likeRepository.findById(id).get().getCompany().equals(companyRepository.findByCredentials_Email(email).get())){
            likeRepository.deleteById(id);
        } else if (developerRepository.findByCredentials_Email(email).isPresent()
                && likeRepository.findById(id).get().getDeveloper().equals(developerRepository.findByCredentials_Email(email).get())) {
            likeRepository.deleteById(id);
        }else throw new CredentialsRequiredException("Credentials required");
    }
    public CollectionModel<EntityModel<LikeDTO>>findByPublicationId(long publicationId)throws NotFoundException{
        if(publicationsRepository.findById(publicationId).isEmpty()){
            throw new NotFoundException("Publication not found");
        }
        PublicationEntity publication = publicationsRepository.findById(publicationId).get();
        return CollectionModel.of(publication.getLikes().stream()
                .map(assembler::toModel)
                .toList());
    }
    public String findLikesQuantityByPublicationId(long publicationId)throws NotFoundException{
        if(publicationsRepository.findById(publicationId).isEmpty()){
            throw new NotFoundException("Publication not found");
        }
        PublicationEntity publication = publicationsRepository.findById(publicationId).get();
        return "Total likes of publication"+ publication.getId()+ ": " + publication.getLikes().size();
    }
}
