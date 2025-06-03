package TP_Final.devhire.Mappers;

import TP_Final.devhire.DTOS.CompanyCommentDTO;
import TP_Final.devhire.DTOS.DevCommentDTO;
import TP_Final.devhire.Entities.CommentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    @Autowired
    private ModelMapper modelMapper;

    public DevCommentDTO convertToDevCommentDTO(CommentEntity comment){
        return modelMapper.map(comment, DevCommentDTO.class);
    }
    public CompanyCommentDTO convertToCompanyCommentDTO(CommentEntity comment){
        return modelMapper.map(comment, CompanyCommentDTO.class);
    }
    public CommentEntity convertToEntity(DevCommentDTO devCommentDTO){
        return modelMapper.map(devCommentDTO, CommentEntity.class);
    }
    public CommentEntity convertToEntity(CompanyCommentDTO companyCommentDTO){
        return modelMapper.map(companyCommentDTO, CommentEntity.class);
    }
}
