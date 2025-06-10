package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantsRepository extends JpaRepository<ApplicationEntity, Long>{
}
