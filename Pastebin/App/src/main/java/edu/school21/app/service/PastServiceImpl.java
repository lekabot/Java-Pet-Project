package edu.school21.app.service;

import org.springframework.stereotype.Service;

@Service
public class PastServiceImpl implements PastService {
    @Override
    public String saveText(String data, Long expirationTimeSeconds) {
        return null;
    }

    @Override
    public String getTextByHash() {
        return null;
    }

    @Override
    public void deleteTextByHash(String hash) {

    }
}
