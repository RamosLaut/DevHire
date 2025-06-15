package TP_Final.devhire.Model.DTOS;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeDTO {
    private long id;
    private String name;
    private String publicationContent;
    private LocalDateTime date;
}
