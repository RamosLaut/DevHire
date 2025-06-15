package TP_Final.devhire.Repositories;

import TP_Final.devhire.Model.Entities.CompanyEntity;
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
    @Query("UPDATE CompanyEntity SET name = :name, location = :location, description = :description WHERE id = :id")
    void update(@Param("name")String name, @Param("location")String location, @Param("description")String description, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CompanyEntity c SET c.enabled = false WHERE c.id = :id")
    void logicDown(@Param("id")long id);
    @Query("SELECT c FROM CompanyEntity c WHERE c.credentials.email = :email")

   Optional<CompanyEntity> findByCredentials_Email(@Param("email") String email);

    Optional<CompanyEntity> findByName(String name);
    void deleteByName(String name);


}
