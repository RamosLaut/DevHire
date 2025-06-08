package TP_Final.devhire.DTOS;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyLikeDTO {
    private long id;
    private long CompanyId;
    private String publicationContent;
    private Timestamp date;
}
