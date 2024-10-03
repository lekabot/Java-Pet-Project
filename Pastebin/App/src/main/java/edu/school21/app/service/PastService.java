package edu.school21.app.service;

import org.springframework.stereotype.Service;

@Service
public interface PastService {

    String saveText(String data, Long expirationTimeSeconds);

    String getTextByHash();

    void deleteTextByHash(String hash);
}
