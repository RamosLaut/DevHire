package TP_Final.devhire.Model.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String name;
    private String publicationContent;
    @NotBlank(message = "Comment content cannot be empty")
    private String content;
    private LocalDateTime commentDate = LocalDateTime.now();
}
