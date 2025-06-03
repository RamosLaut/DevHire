package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE CommentEntity SET content = :content WHERE id = :id")
    void updateContent(@Param("content") String content, @Param("id") Long id);
    Optional<List<CommentEntity>> findByPublicationId(Long publicationId);
}
