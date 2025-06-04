package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.Follow.CompanyFollowsDeveloper;
import TP_Final.devhire.Entities.Follow.CompanyFollowsDeveloperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyFollowDeveloperRepository extends JpaRepository<CompanyFollowsDeveloper, CompanyFollowsDeveloperId> {
    List<CompanyFollowsDeveloper> findByIdDeveloperFollowedIdAndEnabledTrue(Long developerId);

    List<CompanyFollowsDeveloper> findByIdCompanyFollowerIdAndEnabledTrue(Long CompanyId);
}
