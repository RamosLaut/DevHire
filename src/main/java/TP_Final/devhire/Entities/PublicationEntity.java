package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "publications")
public class PublicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String content;
    private Timestamp publicationDate = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")));
    @NotNull
    private Boolean state = true;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_user_publication"))
    private UserEntity user;
    @OneToMany(mappedBy = "publication")
    private List<LikeEntity>likes;
    @OneToMany(mappedBy = "publication")
    private List<CommentEntity>comments;

}
