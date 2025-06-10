package TP_Final.devhire.Entities;

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
    private long id;
    @NotEmpty
    private String name;
    @NotNull
    private String location;
    private String description;
    @NotNull
    private Boolean state;
    @OneToMany(mappedBy = "companyFollower")
    private List<FollowEntity> followed = new ArrayList<>();

    @OneToMany(mappedBy = "companyFollowed")
    private List<FollowEntity> followers = new ArrayList<>();

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
}
