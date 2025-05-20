package TP_Final.devhire.DTOS;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class CommentDTO {
    private Long user_id;
    private long comment_id;
    private long publication_id;
    private String content;
    private Timestamp commentDate;

}
