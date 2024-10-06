package edu.school21.app.service;

import edu.school21.app.models.hash.HashEntity;
import edu.school21.app.models.past.PastEntity;
import edu.school21.app.repository.past.PastRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PastServiceImpl implements PastService {

    private HashService hashService;
    private RemoteStorageService remoteStorageService;
    private PastRepository pastRepository;

    @Autowired
    public PastServiceImpl(HashService hashService, RemoteStorageService remoteStorageService, PastRepository pastRepository) {
        this.hashService = hashService;
        this.remoteStorageService = remoteStorageService;
        this.pastRepository = pastRepository;
    }

    @Override
    @Transactional
    public String saveText(String data, Long expirationTimeSeconds) {
        String hash = hashService.createHash();
        hashService.saveHash(hash);

        remoteStorageService.upload(hash, data);

        PastEntity pastEntity = new PastEntity();
        pastEntity.setHash(hash);
        pastEntity.setExpirationTimeSeconds(expirationTimeSeconds);
        pastEntity.setCreatedAt(System.currentTimeMillis());
        pastRepository.save(pastEntity);

        return hash;
    }

    @Override
    public String getTextByHash(String hash) {
        Optional<HashEntity> hashEntity = hashService.getHashEntity(hash);
        if (hashEntity.isEmpty()) {
            throw new RuntimeException("Hash does not exist");
        }

        return remoteStorageService.read(hash);
    }

    @Override
    @Transactional
    public void deleteTextByHash(String hash) {
        hashService.deleteHash(hash);
        remoteStorageService.delete(hash);

        PastEntity pastEntity = getPastByHash(hash)
            .orElseThrow(() -> new RuntimeException("Hash not found in the database: " + hash));
        pastRepository.delete(pastEntity);
    }

    @Override
    public Optional<PastEntity> getPastByHash(String hash) {
        return pastRepository.findByHash(hash);
    }
}
