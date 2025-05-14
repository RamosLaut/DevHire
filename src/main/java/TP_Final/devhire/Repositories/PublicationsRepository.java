package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.PublicationEntity;
import TP_Final.devhire.Entities.UserEntity;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationsRepository extends JpaRepository<PublicationEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE publications p SET p.content = :content WHERE p.publication_id = :id")
    void updateContent(@Param("content") String content, @Param("id") Long id);

    Optional<List<PublicationEntity>> findByuserId(UserEntity user);

    Optional<PublicationEntity> findById(@NonNull Long id);

    void deleteById(@NonNull Long id);
    @Modifying
    @Transactional
    @Query("DELETE FROM publications p WHERE p.user_id = :id")
    void deleteByuserId(@Param("userId") Long userId);
}
