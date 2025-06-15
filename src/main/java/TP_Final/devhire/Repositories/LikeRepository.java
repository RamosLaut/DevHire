package TP_Final.devhire.Repositories;

import TP_Final.devhire.Model.Entities.LikeEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    List<LikeEntity> findByPublicationId(Long publicationId);
}
