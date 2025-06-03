package TP_Final.devhire.Entities.Follow;

import TP_Final.devhire.Entities.CompanyEntity;
import TP_Final.devhire.Entities.DeveloperEntity;
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
@Table(name = "developer_follows_company")
public class DeveloperFollowsCompany {
    @EmbeddedId
    private DeveloperFollowsCompanyId id;

    private LocalDateTime followedAt;
    @NotNull
    private Boolean enabled;

    @ManyToOne
    @MapsId("developerFollowerId")
    @JoinColumn(name = "developer_follower_id")
    private DeveloperEntity follower;

    @ManyToOne
    @MapsId("companyFollowedId")
    @JoinColumn(name = "company_followed_id")
    private CompanyEntity followed;


    @PrePersist
    public void prePersist() {
        this.followedAt = LocalDateTime.now();
        this.enabled = true;
    }
}
