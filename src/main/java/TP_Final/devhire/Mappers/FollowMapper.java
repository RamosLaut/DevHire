package TP_Final.devhire.Mappers;
import TP_Final.devhire.DTOS.FollowDTO;
import TP_Final.devhire.Entities.FollowEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class FollowMapper {
    @Autowired
    private ModelMapper modelMapper;
    public FollowDTO convertToDTO(FollowEntity follow){
        return modelMapper.map(follow,FollowDTO.class);
    }
    public FollowEntity convertToEntity(FollowDTO followDTO){
        return modelMapper.map(followDTO,FollowEntity.class);
    }
}
