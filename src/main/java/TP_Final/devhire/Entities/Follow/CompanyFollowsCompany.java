package TP_Final.devhire.Entities.Follow;

import TP_Final.devhire.Entities.CompanyEntity;
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
@Table(name = "company_follows_company")
public class CompanyFollowsCompany implements IFollowRelation {
    @EmbeddedId
    private CompanyFollowsCompanyId id;

    private LocalDateTime followedAt;
    @NotNull
    private Boolean enabled;

    @ManyToOne
    @MapsId("companyFollowerId")
    @JoinColumn(name = "company_follower_id")
    private CompanyEntity follower;

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

