package edu.school21.app.service.scheduler;

import edu.school21.app.models.past.PastEntity;
import edu.school21.app.repository.past.PastRepository;
import edu.school21.app.service.PastService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpirationWorker implements ExpirationWorkerInterface {

    private final PastService pastService;
    private final PastRepository pastRepository;

    public ExpirationWorker(PastService pastService, PastRepository pastRepository) {
        this.pastService = pastService;
        this.pastRepository = pastRepository;
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void deleteExpiredTexts() {
        Long currentTime = System.currentTimeMillis();

        List<PastEntity> allEntities = pastRepository.findAll();

        for (PastEntity entity : allEntities) {
            Long expirationTime = entity.getCreatedAt() + entity.getExpirationTimeSeconds() * 1000;
            if (currentTime >= expirationTime) {
                pastService.deleteTextByHash(entity.getHash());
            }
        }
    }
}
