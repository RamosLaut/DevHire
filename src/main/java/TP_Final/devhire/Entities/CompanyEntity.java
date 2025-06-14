package TP_Final.devhire.Entities;

import TP_Final.devhire.Entities.Follow.CompanyFollowsCompany;
import TP_Final.devhire.Entities.Follow.CompanyFollowsDeveloper;
import TP_Final.devhire.Entities.Follow.DeveloperFollowsCompany;
import TP_Final.devhire.Security.Entities.CredentialsEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
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
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String name;
    private String location;
    private String description;
    private Boolean enabled = true;
    @OneToMany(mappedBy = "company")
    private List<JobEntity> jobs;
    @OneToMany(mappedBy = "company")
    private List<PublicationEntity>publications;
    @OneToMany(mappedBy = "company")
    private List<CommentEntity>comments;
    @OneToMany(mappedBy = "company")
    private List<LikeEntity>likes;
    @OneToOne
    @JoinColumn(name = "user_id")
    private CredentialsEntity credentials;

    //Seguimientos:
    @OneToMany(mappedBy = "follower")
    private List<CompanyFollowsCompany> companiesFollowed = new ArrayList<>();

    @OneToMany(mappedBy = "followed")
    private List<CompanyFollowsCompany> companyFollowings = new ArrayList<>();

    @OneToMany(mappedBy = "followed")
    private List<DeveloperFollowsCompany> developerFollowings = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    private List<CompanyFollowsDeveloper> developersFollowed = new ArrayList<>();
}
