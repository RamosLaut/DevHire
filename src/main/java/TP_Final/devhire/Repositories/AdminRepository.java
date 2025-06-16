package TP_Final.devhire.Repositories;

import TP_Final.devhire.Model.Entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long>{
    @Query("SELECT c FROM AdminEntity c WHERE c.credentials.email = :email")
    Optional<AdminEntity> findByCredentials_Email(@Param("email") String email);
}
