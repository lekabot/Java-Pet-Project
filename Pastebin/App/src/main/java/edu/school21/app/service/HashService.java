package edu.school21.app.service;

import edu.school21.app.models.hash.HashEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface HashService {
    String createHash();
    void saveHash(String hash);
    String readHash(String hash);
    boolean isNotExist(String hash);
    void deleteHash(String hash);
    Optional<HashEntity> getHashEntity(String hash);
}
