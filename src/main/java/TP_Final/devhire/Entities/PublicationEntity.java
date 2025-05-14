package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
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
    private long publication_id;
    @NotEmpty
    private String content;
    private Timestamp publicationDate;
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
