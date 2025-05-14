package TP_Final.devhire.Services;

import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Entities.UserEntity;
import TP_Final.devhire.Exceptions.UserNotFoundException;
import TP_Final.devhire.Repositories.PublicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PublicationService{
    private final PublicationsRepository publicationsRepository;
    private final UserService userService;
    @Autowired
    public PublicationService(PublicationsRepository publicationsRepository, UserService userService) {
        this.publicationsRepository = publicationsRepository;
        this.userService = userService;
    }
    public void save(PublicationEntity publicationEntity){
        publicationsRepository.save(publicationEntity);
    }
    public List<PublicationEntity> findAll(){
        return publicationsRepository.findAll();
    }
    public PublicationEntity findById(Long id)throws RuntimeException{
        return publicationsRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    public List<PublicationEntity> findByuserId(Long id)throws UserNotFoundException {
        Optional<UserEntity> user = userService.findById(id);
        return publicationsRepository.findByuserId(user.get()).orElseThrow(()->new UserNotFoundException("User not found"));
    }
    public  void deleteById(Long id){
        publicationsRepository.deleteById(id);
    }
    public void deleteByuserId(Long id)throws UserNotFoundException{
        Optional<UserEntity> user = userService.findById(id);
        if(user.isPresent()){
            publicationsRepository.deleteByuserId(user.get().getUser_id());
        }else{
            throw new UserNotFoundException("User not found");
        }
    }
    public void updateContent(PublicationEntity publicationEntity){
        publicationsRepository.updateContent(publicationEntity.getContent(), publicationEntity.getPublication_id());
    }

}
