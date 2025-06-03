package TP_Final.devhire.DTOS;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperCommentDTO {
    private long id;
    private String Name;
    private long publicationID;
    private String Content;
    private Timestamp Date;
}
