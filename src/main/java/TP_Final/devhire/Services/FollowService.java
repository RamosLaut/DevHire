package TP_Final.devhire.Services;

import TP_Final.devhire.Assemblers.FollowAssembler;
import TP_Final.devhire.DTOS.FollowDTO;
import TP_Final.devhire.Entities.FollowEntity;
import TP_Final.devhire.Entities.FollowId;
import TP_Final.devhire.Exceptions.FollowUserAlreadyExistIException;
import TP_Final.devhire.Repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private FollowRepository followRepository;
    private FollowAssembler assembler;
    @Autowired
    public FollowService(FollowRepository followRepository, FollowAssembler assembler) {
        this.followRepository = followRepository;
        this.assembler = assembler;
    }
public EntityModel<FollowDTO> Save(FollowEntity follow)throws FollowUserAlreadyExistIException {
        boolean exists = followRepository.findAll().stream()
                .map(follow::getId);
        if(exists){
            throw new FollowUserAlreadyExistIException("The id already exists ");
        }
        followRepository.save(follow);
        return assembler.toModel(follow);
}
public void  deleteById(FollowId Idx){
        deleteById(Idx);
}
}
