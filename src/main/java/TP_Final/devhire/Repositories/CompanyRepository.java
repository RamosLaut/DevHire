package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.CompanyEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE CompanyEntity c SET c.name = :name, c.location = :location, c.description = :description WHERE c.id = :id")
    void update(@Param("name")String name, @Param("location")String location, @Param("description")String description, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CompanyEntity c SET c.state = false WHERE c.id = :id")
    void logicDown(@Param("id")long id);
//    @Query("SELECT c FROM CompanyEntity c WHERE c.getCredentials.email = :email")
   Optional<CompanyEntity> findByCredentials_Email(@Param("email") String email);
}
