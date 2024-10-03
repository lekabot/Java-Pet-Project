package edu.school21.app.service;

import edu.school21.app.api.request.PastSaveRequest;
import edu.school21.app.models.HashEntity;
import edu.school21.app.repository.HashRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class HashServiceImpl implements HashService {

    private final HashRepository hashRepository;

    public HashServiceImpl(HashRepository hashRepository) {
        this.hashRepository = hashRepository;
    }

    @Override
    public String createHash() {
        return UUID.randomUUID().toString().substring(0, 32);
    }

    @Override
    @Transactional
    public void saveHash(String hash) {
        if (isNotExist(hash)) {
            HashEntity hashEntity = new HashEntity();
            hashEntity.setIsActive(true);
            hashEntity.setCreatedAt(System.currentTimeMillis());

            hashRepository.save(hashEntity);
        }
    }

    @Override
    public String readHash(String hash) {
        Optional<HashEntity> hashEntity = hashRepository.findByHash(hash);
        if (hashEntity.isPresent()) {
            return hashEntity.get().getHash();
        } else {
            throw new RuntimeException("hashIsNotExist");
        }
    }

    @Override
    public boolean isNotExist(String hash) {
        return hashRepository.findByHash(hash).isEmpty();
    }

    @Override
    public void deleteHash(String hash) {
        hashRepository.deleteByHash(hash);
    }
}
