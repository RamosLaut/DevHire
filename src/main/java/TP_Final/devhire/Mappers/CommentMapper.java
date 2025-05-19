package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.CommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    @Autowired
    private ModelMapper modelMapper;

    public CommentDTO convertToDTO(CommentEntity comment){
        return modelMapper.map(comment, CommentDTO.class);
    }
    public CommentEntity convertToEntity(CommentDTO commentDTO){
        return modelMapper.map(commentDTO, CommentEntity.class);
    }
}
