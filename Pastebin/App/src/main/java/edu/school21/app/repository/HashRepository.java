package edu.school21.app.repository;

import edu.school21.app.models.HashEntity;
import edu.school21.app.models.PastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashRepository extends JpaRepository<HashEntity, Long> {
    Optional<HashEntity> findByHash(String hash);
    void deleteByHash(String hash);
}
