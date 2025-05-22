package TP_Final.devhire.Repositories;

import TP_Final.devhire.Entities.CommentEntity;
import TP_Final.devhire.Entities.LikeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE CommentEntity p SET p.content = :content WHERE p.comment_id = :id")
    void updateContent(@Param("content") String content, @Param("id") Long id);
    Optional<List<CommentEntity>> findByPublicationId(Long publicationId);
}
