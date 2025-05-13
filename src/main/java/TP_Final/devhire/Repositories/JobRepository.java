package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
}
