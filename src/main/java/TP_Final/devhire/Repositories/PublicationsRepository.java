package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.PublicationEntity;
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
    @Query("UPDATE PublicationEntity SET content = :content WHERE id = :id")
    void updateContent(@Param("content") String content, @Param("id") Long id);

    List<PublicationEntity> findByDeveloperId(long userId);
    List<PublicationEntity> findByCompanyId(long companyId);
    @NonNull Optional<PublicationEntity> findById(@NonNull Long id);

    void deleteById(@NonNull Long id);

}
