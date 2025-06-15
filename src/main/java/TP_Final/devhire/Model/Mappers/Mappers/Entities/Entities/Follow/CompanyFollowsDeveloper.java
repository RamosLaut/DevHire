package TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow;

import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
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
@Table(name = "company_follows_developer")
public class CompanyFollowsDeveloper implements IFollowRelation {
    @EmbeddedId
    private CompanyFollowsDeveloperId id;

    private LocalDateTime followedAt;
    @NotNull
    private Boolean enabled;

    @ManyToOne
    @MapsId("companyFollowerId")
    @JoinColumn(name = "company_follower_id")
    private CompanyEntity follower;

    @ManyToOne
    @MapsId("developerFollowedId")
    @JoinColumn(name = "developer_followed_id")
    private DeveloperEntity followed;


    @PrePersist
    public void prePersist() {
        this.followedAt = LocalDateTime.now();
        this.enabled = true;
    }
}
