package TP_Final.devhire.DTOS;

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
