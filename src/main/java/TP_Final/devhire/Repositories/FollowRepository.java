package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.FollowEntity;
import TP_Final.devhire.Entities.FollowId;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository <FollowEntity, FollowId> {
}
