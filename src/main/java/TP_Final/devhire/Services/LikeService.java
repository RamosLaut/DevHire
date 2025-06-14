package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.LikeAssembler;
import TP_Final.devhire.DTOS.LikeDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.DeveloperEntity;
import TP_Final.devhire.Entities.LikeEntity;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Exceptions.AlreadyExistsException;
import TP_Final.devhire.Exceptions.CredentialsRequiredException;
import TP_Final.devhire.Exceptions.NotFoundException;
import TP_Final.devhire.Exceptions.UnauthorizedException;
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
import java.util.Optional;
import java.util.stream.Stream;

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
        like.setPublication(publicationsRepository.findById(publicationId).get());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            Optional<LikeEntity> optLike = company.getLikes().stream()
                    .filter(l -> publication.equals(like.getPublication()))
                    .findFirst();
            if(optLike.isPresent()){
                throw new AlreadyExistsException("Like already exists");
            }
            like.setCompany(company);
        } else if (developerRepository.findByCredentials_Email(email).isPresent()) {
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            Optional<LikeEntity> optLike = developer.getLikes().stream()
                    .filter(l -> publication.equals(like.getPublication()))
                    .findFirst();
            if(optLike.isPresent()){
                throw new AlreadyExistsException("Like already exists");
            }
            like.setDeveloper(developer);
        }else throw new CredentialsRequiredException("Credentials required");
        likeRepository.save(like);
        return assembler.toModel(like);
    }
    public CollectionModel<EntityModel<LikeDTO>> findAll(){
        List<LikeEntity> likes = likeRepository.findAll();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            List<EntityModel<LikeDTO>> ownLikes = likes.stream()
                    .filter(like -> company.equals(like.getCompany()))
                    .map(assembler::toOwnLikeModel)
                    .toList();
            List<EntityModel<LikeDTO>> otherLikes = likes.stream().map(assembler::toModel).toList();
            List <EntityModel<LikeDTO>> allLikes = Stream.concat(ownLikes.stream(), otherLikes.stream()).toList();
            if (allLikes.isEmpty()) {
                throw new NotFoundException("No likes found");
            }
            return CollectionModel.of(allLikes);
        }
        else if(developerRepository.findByCredentials_Email(email).isPresent()){
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            List<EntityModel<LikeDTO>> ownLikes = likes.stream()
                    .filter(like -> like.getDeveloper().equals(developer))
                    .map(assembler::toOwnLikeModel)
                    .toList();
            List<EntityModel<LikeDTO>> otherLikes = likes.stream().map(assembler::toModel).toList();
            return CollectionModel.of(Stream.concat(ownLikes.stream(), otherLikes.stream()).toList());
        }else throw new CredentialsRequiredException("You need to be logged in to see likes list");
    }
    public CollectionModel<EntityModel<LikeDTO>> findOwnLikes(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            List<EntityModel<LikeDTO>> likes = companyRepository.findByCredentials_Email(email).get()
                    .getLikes()
                    .stream()
                    .map(assembler::toOwnLikeModel)
                    .toList();
            if (likes.isEmpty()) {
                throw new NotFoundException("No likes found");
            }
            return CollectionModel.of(likes);
        }else if(developerRepository.findByCredentials_Email(email).isPresent()){
            List<EntityModel<LikeDTO>> likes = developerRepository.findByCredentials_Email(email).get()
                    .getLikes()
                    .stream()
                    .map(assembler::toOwnLikeModel)
                    .toList();
            if(likes.isEmpty()){
                throw new NotFoundException("No likes found");
            }
            return CollectionModel.of(likes);
        }else throw new CredentialsRequiredException("You need to be logged in to see your likes");
    }
    public EntityModel<LikeDTO> findById(long id)throws NotFoundException, CredentialsRequiredException{
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(likeRepository.findById(id).isEmpty()){
            throw new NotFoundException("Like not found");
        }
        LikeEntity like = likeRepository.findById(id).get();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            if(company.getLikes().contains(like)){
                return assembler.toOwnLikeModel(like);
            }else return assembler.toModel(like);
        }else if(developerRepository.findByCredentials_Email(email).isPresent()){
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            if(developer.getLikes().contains(like)){
                return assembler.toOwnLikeModel(like);
            }else return assembler.toModel(like);
        }else throw new CredentialsRequiredException("You need to be logged in to see likes list");
    }
    public void deleteById(long id)throws NotFoundException {
        if(likeRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Like not found");
        }
        LikeEntity like = likeRepository.findById(id).get();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(companyRepository.findByCredentials_Email(email).isPresent()){
            CompanyEntity company = companyRepository.findByCredentials_Email(email).get();
            if(company.getLikes().contains(like)){
                likeRepository.deleteById(id);
            }else throw new UnauthorizedException("You don't have permission to unlike this publication");
        }
        if (developerRepository.findByCredentials_Email(email).isPresent()){
            DeveloperEntity developer = developerRepository.findByCredentials_Email(email).get();
            if(developer.getLikes().contains(like)){
                likeRepository.deleteById(id);
            }else throw new UnauthorizedException("You don't have permission to unlike this publication");
        }
    }
    public CollectionModel<EntityModel<LikeDTO>>findByPublicationId(long publicationId)throws NotFoundException{
        if(publicationsRepository.findById(publicationId).isEmpty()){
            throw new NotFoundException("Publication not found");
        }
        PublicationEntity publication = publicationsRepository.findById(publicationId).get();
        List<EntityModel<LikeDTO>> likes = publication.getLikes().stream()
                .map(assembler::toModel)
                .toList();
        if(likes.isEmpty()){
            throw new NotFoundException("No likes found for this publication");
        }
        return CollectionModel.of(likes);
    }
    public String findLikesQuantityByPublicationId(long publicationId)throws NotFoundException{
        if(publicationsRepository.findById(publicationId).isEmpty()){
            throw new NotFoundException("Publication not found");
        }
        PublicationEntity publication = publicationsRepository.findById(publicationId).get();
        return "Total likes of publication "+ publication.getId()+ ": " + publication.getLikes().size();
    }
}
