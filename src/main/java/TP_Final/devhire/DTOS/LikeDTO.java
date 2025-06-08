package TP_Final.devhire.DTOS;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeDTO {
    private long id;
    private String name;
    private String publicationContent;
    private Timestamp date;
}
