package TP_Final.devhire.Security.Repositorys;

import TP_Final.devhire.Security.Entitys.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Long> {
    Optional<UserDetails> findByEmail(String email);
}
