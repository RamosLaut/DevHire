package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.PublicationAssembler;
import TP_Final.devhire.DTOS.PublicationDTO;
import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Exceptions.UserNotFoundException;
import TP_Final.devhire.Repositories.CompanyRepository;
import TP_Final.devhire.Repositories.PublicationsRepository;
import TP_Final.devhire.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublicationService{
    private final PublicationsRepository publicationsRepository;
    private final PublicationAssembler publicationAssembler;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    @Autowired
    public PublicationService(PublicationsRepository publicationsRepository, PublicationAssembler publicationAssembler, CompanyRepository companyRepository, UserRepository userRepository) {
        this.publicationsRepository = publicationsRepository;
        this.publicationAssembler = publicationAssembler;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public EntityModel<PublicationDTO> save(PublicationEntity publicationEntity) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CompanyEntity> companyOpt = companyRepository.findByEmail(email);
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (companyOpt.isPresent()) {
            publicationEntity.setCompany(companyOpt.get());
        } else if (userOpt.isPresent()) {
            publicationEntity.setUser(userOpt.get());
        }
        publicationsRepository.save(publicationEntity);
        return publicationAssembler.toModel(publicationEntity);
    }
    public CollectionModel<EntityModel<PublicationDTO>> findAll(){
        return CollectionModel.of(publicationsRepository.findAll().stream()
                .map(publicationAssembler::toModel)
                .toList());
    }
    public EntityModel<PublicationDTO> findById(Long id)throws RuntimeException{
        return publicationAssembler.toModel(publicationsRepository.findById(id).orElseThrow(RuntimeException::new));
    }
    public CollectionModel<EntityModel<PublicationDTO>> findByuserId(Long id)throws UserNotFoundException {
        return CollectionModel.of(publicationsRepository.findByUser_Id(id).stream()
                .map(publicationAssembler::toModel)
                .toList());
    }
    public void deleteById(Long id){
        publicationsRepository.deleteById(id);
    }

    public EntityModel<PublicationDTO> updateContent(PublicationEntity publicationEntity){
        publicationsRepository.updateContent(publicationEntity.getContent(), publicationEntity.getId());
        return publicationAssembler.toModel(publicationEntity);
    }
}
