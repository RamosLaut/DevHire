package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.Follow.CompanyFollowsCompany;
import TP_Final.devhire.Entities.Follow.CompanyFollowsCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyFollowCompanyRepository extends JpaRepository<CompanyFollowsCompany, CompanyFollowsCompanyId> {
}
