package TP_Final.devhire.Model.Mappers;
import TP_Final.devhire.Model.DTOS.LikeDTO;
import TP_Final.devhire.Model.Entities.LikeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {
    @Autowired
    private ModelMapper modelMapper;
    public LikeDTO convertToLikeDTO(LikeEntity like){
        return modelMapper.map(like, LikeDTO.class);
    }
    public LikeEntity convertToEntity(LikeDTO likeDTO){
        return modelMapper.map(likeDTO, LikeEntity.class);
    }
}
