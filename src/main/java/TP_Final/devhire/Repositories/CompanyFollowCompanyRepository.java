package TP_Final.devhire.Repositories;

import TP_Final.devhire.Model.Entities.CompanyEntity;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.CompanyFollowsCompany;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.CompanyFollowsCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyFollowCompanyRepository extends JpaRepository<CompanyFollowsCompany, CompanyFollowsCompanyId> {
    List<CompanyFollowsCompany> findByIdCompanyFollowedIdAndEnabledTrue(Long companyId);
    List<CompanyFollowsCompany> findByIdCompanyFollowerIdAndEnabledTrue(Long companyId);

    long countByFollowerAndEnabledTrue(CompanyEntity follower);
    long countByFollowedAndEnabledTrue(CompanyEntity followed);
}
