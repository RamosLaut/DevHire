package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername (String username);
    Optional<UserEntity> findByEmail (String email);
    Optional<UserEntity> findByDni (String dni);



    Page<UserEntity> findAll (Pageable pageable);


}
