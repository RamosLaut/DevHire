package TP_Final.devhire.Security.Repositories;

import TP_Final.devhire.Security.Entities.PermitEntity;
import TP_Final.devhire.Security.Enums.Permits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermitRepository extends JpaRepository<PermitEntity, Long> {
    Optional<PermitEntity> findByPermit(Permits permit);
}

