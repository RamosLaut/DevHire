package TP_Final.devhire.Model.Entities;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;
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
    private String content;
    private LocalDateTime publicationDate;
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
