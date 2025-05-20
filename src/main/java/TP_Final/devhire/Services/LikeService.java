package TP_Final.devhire.Services;

import TP_Final.devhire.Entities.LikeEntity;
import TP_Final.devhire.Exceptions.LikeNotFoundException;
import TP_Final.devhire.Repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }
    public void save(LikeEntity like){
        likeRepository.save(like);
    }
    public List<LikeEntity> findAll(){
        return likeRepository.findAll();
    }
    public LikeEntity findById(long id)throws LikeNotFoundException{
        return likeRepository.findById(id).orElseThrow(()->new LikeNotFoundException("Like not found"));
    }
    public void deleteById(long id){
        likeRepository.deleteById(id);
    }
    public List<LikeEntity>findByPublicationId(long publicationId){
        return likeRepository.findByPublicationId(publicationId);
    }
}
