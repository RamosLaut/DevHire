package TP_Final.devhire.DTOS;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class DevCommentDTO {
    private long id;
    private String DeveloperName;
    private long publicationID;
    private String content;
    private Timestamp date;
}
