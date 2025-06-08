package TP_Final.devhire.DTOS;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationDTO {
    Long id;
    String name;
    Timestamp date;
    String content;
}
