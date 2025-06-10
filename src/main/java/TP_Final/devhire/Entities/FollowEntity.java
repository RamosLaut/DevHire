package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "followers")
public class FollowEntity {
//    @EmbeddedId
//    private FollowId id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime followUpDate = LocalDateTime.now();
    @NotNull
    private Boolean state;

    @ManyToOne
    @JoinColumn(name = "DevFollower_id")
    private DeveloperEntity devFollower;
    @ManyToOne
    @JoinColumn(name = "DevFollowed_id")
    private DeveloperEntity devFollowed;

    @ManyToOne
    @JoinColumn(name = "CompanyFollower_id")
    private CompanyEntity companyFollower;
    @ManyToOne
    @JoinColumn(name = "CompanyFollowed_id")
    private CompanyEntity companyFollowed;
    @NotNull
    private Boolean acepted;


}
