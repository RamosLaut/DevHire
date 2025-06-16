package TP_Final.devhire.Model.Entities;

import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.CompanyFollowsCompany;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.CompanyFollowsDeveloper;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.DeveloperFollowsCompany;
import TP_Final.devhire.Security.Entities.CredentialsEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

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
