package TP_Final.devhire.Repositories;

import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Entities.DeveloperEntity;

import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.DeveloperFollowsCompany;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.DeveloperFollowsCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperFollowCompanyRepository extends JpaRepository<DeveloperFollowsCompany, DeveloperFollowsCompanyId> {

    List<DeveloperFollowsCompany> findByIdCompanyFollowedIdAndEnabledTrue(Long companyId);

    List<DeveloperFollowsCompany> findByIdDeveloperFollowerIdAndEnabledTrue(Long developerId);

    long countByFollowerAndEnabledTrue(DeveloperEntity follower);
    long countByFollowedAndEnabledTrue(CompanyEntity followed);
}
