package TP_Final.devhire.DTOS;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperPublicationDTO {
    Long id;
    String developerName;
    Timestamp date;
    String content;
}
