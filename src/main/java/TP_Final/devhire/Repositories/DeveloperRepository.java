package TP_Final.devhire.Repositories;

import TP_Final.devhire.Model.Entities.DeveloperEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long> {

//    Optional<DeveloperEntity> findByUsername (String username);
    Optional<DeveloperEntity> findByCredentials_Email(String email);
    Optional<DeveloperEntity> findByDni (String dni);
    @NonNull Page<DeveloperEntity>  findAll (@NonNull Pageable pageable);


}
