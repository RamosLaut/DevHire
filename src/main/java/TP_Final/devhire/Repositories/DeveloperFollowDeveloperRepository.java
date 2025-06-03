package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.Follow.DeveloperFollowsDeveloper;
import TP_Final.devhire.Entities.Follow.DeveloperFollowsDeveloperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperFollowDeveloperRepository extends JpaRepository <DeveloperFollowsDeveloper, DeveloperFollowsDeveloperId> {

}
