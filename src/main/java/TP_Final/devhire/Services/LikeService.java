package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.LikeAssembler;
import TP_Final.devhire.DTOS.LikeDTO;
import TP_Final.devhire.Entities.LikeEntity;
import TP_Final.devhire.Exceptions.LikeNotFoundException;
import TP_Final.devhire.Repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeAssembler assembler;
    @Autowired
    public LikeService(LikeRepository likeRepository, LikeAssembler assembler) {
        this.likeRepository = likeRepository;
        this.assembler = assembler;
    }
    public EntityModel<LikeDTO> save(LikeEntity like){
        likeRepository.save(like);
        return assembler.toModel(like);
    }
    public CollectionModel<EntityModel<LikeDTO>> findAll(){
        List<EntityModel<LikeDTO>> likes = likeRepository.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(likes);
    }
    public EntityModel<LikeDTO> findById(long id)throws LikeNotFoundException{
        return assembler.toModel(likeRepository.findById(id).orElseThrow(()->new LikeNotFoundException("Like not found")));
    }
    public void deleteById(long id){
        likeRepository.deleteById(id);
    }
    public CollectionModel<EntityModel<LikeDTO>>findByPublicationId(long publicationId){
        List<EntityModel<LikeDTO>> likes = likeRepository.findByPublicationId(publicationId).stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(likes);
    }
}
