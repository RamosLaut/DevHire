package TP_Final.devhire.DTOS;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private long id;
    private String name;
    private long publicationID;
    private String content;
    private Timestamp date;
}
