package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.PublicationAssembler;
import TP_Final.devhire.DTOS.PublicationDTO;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Exceptions.UserNotFoundException;
import TP_Final.devhire.Repositories.PublicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@Service
public class PublicationService{
    private final PublicationsRepository publicationsRepository;
    private final PublicationAssembler publicationAssembler;
    @Autowired
    public PublicationService(PublicationsRepository publicationsRepository, PublicationAssembler publicationAssembler) {
        this.publicationsRepository = publicationsRepository;
        this.publicationAssembler = publicationAssembler;
    }

    public EntityModel<PublicationDTO> save(PublicationEntity publicationEntity){
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
    public  void deleteById(Long id){
        publicationsRepository.deleteById(id);
    }

    public EntityModel<PublicationDTO> updateContent(PublicationEntity publicationEntity){
        publicationsRepository.updateContent(publicationEntity.getContent(), publicationEntity.getId());
        return publicationAssembler.toModel(publicationEntity);
    }
}
