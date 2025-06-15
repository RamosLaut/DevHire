package TP_Final.devhire.Repositories;

import TP_Final.devhire.Model.Entities.DeveloperEntity;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.DeveloperFollowsDeveloper;
import TP_Final.devhire.Model.Mappers.Mappers.Entities.Entities.Follow.DeveloperFollowsDeveloperId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperFollowDeveloperRepository extends JpaRepository <DeveloperFollowsDeveloper, DeveloperFollowsDeveloperId> {

    List<DeveloperFollowsDeveloper> findByIdDeveloperFollowedIdAndEnabledTrue(Long developerId);

    List<DeveloperFollowsDeveloper> findByIdDeveloperFollowerIdAndEnabledTrue(Long developerId);

    long countByFollowerAndEnabledTrue(DeveloperEntity follower);
    long countByFollowedAndEnabledTrue(DeveloperEntity followed);
}

