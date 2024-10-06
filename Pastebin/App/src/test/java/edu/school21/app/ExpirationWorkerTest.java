package edu.school21.app;

import edu.school21.app.models.past.PastEntity;
import edu.school21.app.repository.past.PastRepository;
import edu.school21.app.service.PastService;
import edu.school21.app.service.YandexCloudStorageService;
import edu.school21.app.service.scheduler.ExpirationWorker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@EnableScheduling
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ExpirationWorkerTest {

    @Autowired
    private PastService pastService;

    @Autowired
    private PastRepository pastRepository;

    @Autowired
    private ExpirationWorker expirationWorker;

    @Autowired
    private YandexCloudStorageService yandexCloudStorageService;

    private final String testData = "This will expire";

    @BeforeEach
    public void setUp() {
        pastRepository.deleteAll();
        yandexCloudStorageService.deleteAll();
    }

    @Test
    public void testDeleteExpiredTexts() throws InterruptedException {
        Long expirationTimeInSeconds = -10L;
        String hash = pastService.saveText(testData, expirationTimeInSeconds);

        Optional<PastEntity> entityBeforeDeletion = pastRepository.findByHash(hash);
        assertTrue(entityBeforeDeletion.isPresent());

        Thread.sleep(2000);

        expirationWorker.deleteExpiredTexts();

        Optional<PastEntity> entityAfterDeletion = pastRepository.findByHash(hash);
        assertTrue(entityAfterDeletion.isEmpty());
    }



    @Test
    public void testDeleteNonExpiredTexts() {
        Long futureTime = System.currentTimeMillis() + 3600000L;
        String hash = pastService.saveText(testData, futureTime);

        expirationWorker.deleteExpiredTexts();

        Optional<PastEntity> entity = pastRepository.findByHash(hash);
        assertTrue(entity.isPresent());
    }

}
