package edu.school21.app.repository.past;

import edu.school21.app.models.past.PastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PastRepository extends JpaRepository<PastEntity, Long> {
    Optional<PastEntity> findByHash(String hash);
    List<PastEntity> findAllByExpirationTimeSecondsLessThan(Long currentTime);

}
