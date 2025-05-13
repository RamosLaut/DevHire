package TP_Final.devhire.Services;

import TP_Final.devhire.Controllers.Exceptions.UserNotFoundException;
import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Repositories.PublicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PublicationService{
//    @Autowired
    private static PublicationsRepository publicationsRepository;
    @Autowired
    public PublicationService(PublicationsRepository publicationsRepository) {
        this.publicationsRepository = publicationsRepository;
    }

    public static void save(PublicationEntity publicationEntity){
        publicationsRepository.save(publicationEntity);
    }
    public static List<PublicationEntity> findAll(){
        return publicationsRepository.findAll();
    }
    public static PublicationEntity findById(Long id)throws RuntimeException{
        return publicationsRepository.findById(id).orElseThrow(RuntimeException::new);
    }
//    public static List<PublicationEntity> findByuserId(Long id)throws UserNotFoundException {
//        return publicationsRepository.findByuserId(id).orElseThrow(()->new UserNotFoundException("User not found"));
//    }
    public static void deleteById(Long id){
        publicationsRepository.deleteById(id);
    }
//    public static void deleteByuserId(Long id){
//        publicationsRepository.deleteByuserId(id);
//    }
//    public static void updateContent(PublicationEntity publicationEntity){
//        publicationsRepository.updateContent(publicationEntity.getContent(), publicationEntity.getPublication_id());
//    }

}
