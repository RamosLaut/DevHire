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
    private String companyName;
    private long publication_id;
    private String content;
    private Timestamp commentDate;
}
