package TP_Final.devhire.DTOS;

import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Entities.UserEntity;
import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;
@Data
public class CommentDTO {
//    private Long user_id;
    private long comment_id;
//    private long publication_id;
    private String content;
    private Timestamp commentDate;

}
