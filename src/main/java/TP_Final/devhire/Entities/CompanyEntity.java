package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String name;
    @NotNull
    private String location;
    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    private String description;
    @NotNull
    private Boolean state = true;

    @OneToMany(mappedBy = "company")
    private List<JobEntity> jobs;
    @OneToMany(mappedBy = "company")
    private List<PublicationEntity>publications;
    @OneToMany(mappedBy = "company")
    private List<CommentEntity>comments;
    @OneToMany(mappedBy = "company")
    private List<LikeEntity>likes;

}
