package edu.school21.app.repository.hash;

import edu.school21.app.models.hash.HashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashRepository extends JpaRepository<HashEntity, Long> {
    Optional<HashEntity> findByHash(String hash);
    void deleteByHash(String hash);
}
