package TP_Final.devhire.Model.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationDTO {
    private Long id;
    private String name;
    private LocalDateTime publicationDate = LocalDateTime.now();
    @NotBlank(message = "Publication content cannot be empty")
    private String content;
    private int totalLikes;
}
