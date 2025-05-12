package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
