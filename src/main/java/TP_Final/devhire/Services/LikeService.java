package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.LikeAssembler;
import TP_Final.devhire.Entities.LikeEntity;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Exceptions.CredentialsRequiredException;
import TP_Final.devhire.Exceptions.LikeNotFoundException;
import TP_Final.devhire.Exceptions.PublicationNotFoundException;
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
    public EntityModel<Object> save(Long publicationId)throws PublicationNotFoundException, CredentialsRequiredException{
        LikeEntity like = new LikeEntity();
        if(publicationsRepository.findById(publicationId).isEmpty()){
            throw new PublicationNotFoundException("Publication not found");
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
    public CollectionModel<EntityModel<Object>> findAll(){
        List<EntityModel<Object>> likes = likeRepository.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(likes);
    }
    public CollectionModel<EntityModel<Object>> findOwnLikes(){
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
    public EntityModel<Object> findById(long id)throws LikeNotFoundException, CredentialsRequiredException{
        return assembler.toModel(likeRepository.findById(id).orElseThrow(()->new LikeNotFoundException("Like not found")));
    }
    public void deleteById(long id)throws LikeNotFoundException{
        if(likeRepository.findById(id).isEmpty()) {
            throw new LikeNotFoundException("Like not found");
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
    public CollectionModel<EntityModel<Object>>findByPublicationId(long publicationId)throws PublicationNotFoundException{
        if(publicationsRepository.findById(publicationId).isEmpty()){
            throw new PublicationNotFoundException("Publication not found");
        }
        PublicationEntity publication = publicationsRepository.findById(publicationId).get();
        return CollectionModel.of(publication.getLikes().stream()
                .map(assembler::toModel)
                .toList());
    }

    public String findLikesQuantityByPublicationId(long publicationId)throws PublicationNotFoundException{
        if(publicationsRepository.findById(publicationId).isEmpty()){
            throw new PublicationNotFoundException("Publication not found");
        }
        PublicationEntity publication = publicationsRepository.findById(publicationId).get();
        return "Likes: " + publication.getLikes().size();
    }
}
