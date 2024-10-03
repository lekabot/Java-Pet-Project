package edu.school21.app.repository;

import edu.school21.app.models.HashEntity;
import edu.school21.app.models.PastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastRepository extends JpaRepository<PastEntity, Long> {

}
