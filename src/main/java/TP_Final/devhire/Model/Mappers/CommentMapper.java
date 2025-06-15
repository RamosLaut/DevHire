package TP_Final.devhire.Model.Mappers;

import TP_Final.devhire.Model.DTOS.CommentDTO;
import TP_Final.devhire.Model.Entities.CommentEntity;
import TP_Final.devhire.Model.Entities.CommentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    @Autowired
    private ModelMapper modelMapper;

    public CommentDTO convertCommentDTO(CommentEntity comment){
        return modelMapper.map(comment, CommentDTO.class);
    }
    public CommentEntity convertToEntity(CommentDTO commentDTO){
        return modelMapper.map(commentDTO, CommentEntity.class);
    }
}
