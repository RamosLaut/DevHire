package TP_Final.devhire.DTOS;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class DevCommentDTO {
    private long id;
    private String devName;
    private long publication_id;
    private String content;
    private Timestamp commentDate;
}
