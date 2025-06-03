package TP_Final.devhire.DTOS;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperLikeDTO {
    private long id;
    private long devId;
    private long publicationId;
    private Timestamp date;
}
