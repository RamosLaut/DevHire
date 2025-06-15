package TP_Final.devhire.Model.Entities;

import TP_Final.devhire.Model.Mappers.Mappers.Entities.CompanyEntity;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.DeveloperEntity;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

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
    private Long id;
    @ManyToOne
    @JoinColumn(name = "publication_id", foreignKey = @ForeignKey(name = "FK_publication_comment"))
    private PublicationEntity publication;
    @ManyToOne
    @JoinColumn(name = "developer_id", foreignKey = @ForeignKey(name = "FK_dev_comment"))
    private DeveloperEntity developer;
    @ManyToOne
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "FK_company_comment"))
    private CompanyEntity company;
    private String content;
    private LocalDateTime commentDate = LocalDateTime.now();
    private Boolean state = true;
}
