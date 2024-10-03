package edu.school21.app.service;

import edu.school21.app.api.request.PastSaveRequest;
import org.springframework.stereotype.Service;

@Service
public interface HashService {
    String createHash();
    void saveHash(String hash);
    String readHash(String hash);
    boolean isNotExist(String hash);
    void deleteHash(String hash);
}
