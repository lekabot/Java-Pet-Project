package edu.school21.app.service;

import edu.school21.app.models.past.PastEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PastService {

    String saveText(String data, Long expirationTimeSeconds);

    String getTextByHash(String hash);

    void deleteTextByHash(String hash);

    Optional<PastEntity> getPastByHash(String hash);
}
