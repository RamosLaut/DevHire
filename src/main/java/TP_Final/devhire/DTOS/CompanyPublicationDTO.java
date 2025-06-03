package TP_Final.devhire.DTOS;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyPublicationDTO {
    Long id;
    String CompanyName;
    Timestamp date;
    String content;
}
