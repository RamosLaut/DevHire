package TP_Final.devhire.Repositories;

import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.CompanyFollowsDeveloper;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.CompanyFollowsDeveloperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyFollowDeveloperRepository extends JpaRepository<CompanyFollowsDeveloper, CompanyFollowsDeveloperId> {
    List<CompanyFollowsDeveloper> findByIdDeveloperFollowedIdAndEnabledTrue(Long developerId);

    List<CompanyFollowsDeveloper> findByIdCompanyFollowerIdAndEnabledTrue(Long companyId);
    long countByFollowerAndEnabledTrue(CompanyEntity follower);
    long countByFollowedAndEnabledTrue(DeveloperEntity followed);
}
