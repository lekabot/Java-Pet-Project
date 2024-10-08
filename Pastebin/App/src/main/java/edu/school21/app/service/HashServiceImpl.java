package edu.school21.app.service;

import edu.school21.app.models.hash.HashEntity;
import edu.school21.app.repository.hash.HashRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class HashServiceImpl implements HashService {

    private final HashRepository hashRepository;
    private final RedisTemplate<String, String> redisTemplateForHash;

    public HashServiceImpl(HashRepository hashRepository,
                           @Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplateForHash) {
        this.hashRepository = hashRepository;
        this.redisTemplateForHash = redisTemplateForHash;
    }

    @Override
    public String createHash() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    @Transactional
    public void saveHash(String hash) {
        while (!isNotExist(hash)) {
            hash = createHash();
        }

        HashEntity hashEntity = new HashEntity();
        hashEntity.setHash(hash);
        hashEntity.setIsActive(true);
        hashEntity.setCreatedAt(System.currentTimeMillis());

        redisTemplateForHash.opsForValue().set(hash, "active");

        hashRepository.save(hashEntity);
    }

    @Override
    public String readHash(String hash) {

        String cachedHash = redisTemplateForHash.opsForValue().get(hash);
        if (cachedHash != null) {
            return hash;
        }

        Optional<HashEntity> hashEntity = hashRepository.findByHash(hash);
        if (hashEntity.isPresent()) {
            return hashEntity.get().getHash();
        } else {
            throw new RuntimeException("hashIsNotExist");
        }
    }

    @Override
    public boolean isNotExist(String hash) {
        String cashedHash = redisTemplateForHash.opsForValue().get(hash);

        if (cashedHash != null) {
            return false;
        }

        return hashRepository.findByHash(hash).isEmpty();
    }

    @Override
    @Transactional
    public void deleteHash(String hash) {
        redisTemplateForHash.delete(hash);
        hashRepository.deleteByHash(hash);
    }

    @Override
    public Optional<HashEntity> getHashEntity(String hash) {
        return hashRepository.findByHash(hash);
    }
}
