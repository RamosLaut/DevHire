package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.Follow.DeveloperFollowsCompany;
import TP_Final.devhire.Entities.Follow.DeveloperFollowsCompanyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperFollowCompanyRepository extends JpaRepository<DeveloperFollowsCompany, DeveloperFollowsCompanyId> {

}
