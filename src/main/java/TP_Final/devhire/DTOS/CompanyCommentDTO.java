package TP_Final.devhire.DTOS;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyCommentDTO {
    private long id;
    private String CompanyName;
    private long publicationID;
    private String Content;
    private Timestamp Date;
}
