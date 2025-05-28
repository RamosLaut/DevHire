package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<JobEntity, Long> {

}
