package edu.school21.app.service;

import java.io.File;

public interface RemoteStorageService {
    void upload(String hash, String data);
    void delete(String hash);
    String read(String hash);
    void deleteAll();
}
