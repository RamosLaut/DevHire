package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long comment_id;
    @ManyToOne
    @JoinColumn(name = "publication_id", foreignKey = @ForeignKey(name = "FK_publication_comment"))
    private PublicationEntity publication_id;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_user_comment"))
    private UserEntity user_id;
    @NotEmpty
    private String content;
    private Timestamp commentDate;
    @NotNull
    private Boolean state;
}
