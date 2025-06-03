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
    private Long id;
    @NotEmpty
    private String content;
    private Timestamp publicationDate = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")));
    @NotNull
    private Boolean state = true;
    @ManyToOne
    @JoinColumn(name = "dev_id", foreignKey = @ForeignKey(name = "FK_dev_publication"))
    private DeveloperEntity developer;
    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_company_publication"))
    private CompanyEntity company;
    @OneToMany(mappedBy = "publication")
    private List<LikeEntity>likes;
    @OneToMany(mappedBy = "publication")
    private List<CommentEntity>comments;

}
