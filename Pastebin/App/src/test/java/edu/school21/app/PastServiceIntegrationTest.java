package edu.school21.app;

import edu.school21.app.models.past.PastEntity;
import edu.school21.app.repository.past.PastRepository;
import edu.school21.app.service.PastService;
import edu.school21.app.service.RemoteStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PastServiceIntegrationTest {

    @Autowired
    private PastService pastService;

    @Autowired
    private PastRepository pastRepository;

    @Autowired
    private RemoteStorageService remoteStorageService;

    @BeforeEach
    public void setUp() {
        remoteStorageService.deleteAll();
    }

    @Test
    public void testSaveText() {
        String data = "Test data";
        Long expirationTime = 10L;

        String hash = pastService.saveText(data, expirationTime);

        Optional<PastEntity> savedEntity = pastRepository.findByHash(hash);
        assertTrue(savedEntity.isPresent());
        assertEquals(hash, savedEntity.get().getHash());
        assertEquals(expirationTime, savedEntity.get().getExpirationTimeSeconds());

        String storedData = remoteStorageService.read(hash);
        assertEquals(data, storedData);
    }

    @Test
    public void testGetTextByHash() {
        String data = "Another test data";
        Long expirationTime = 10L;

        String hash = pastService.saveText(data, expirationTime);

        Optional<PastEntity> savedEntity = pastRepository.findByHash(hash);
        System.out.println(savedEntity);

        String retrievedData = pastService.getTextByHash(hash);
        assertEquals(data, retrievedData);
    }

    @Test
    public void testDeleteTextByHash() {
        String data = "Data to delete";
        Long expirationTime = 3600L;

        String hash = pastService.saveText(data, expirationTime);

        assertNotNull(pastService.getTextByHash(hash));

        pastService.deleteTextByHash(hash);

        assertTrue(pastRepository.findByHash(hash).isEmpty());

        assertThrows(RuntimeException.class, () -> remoteStorageService.read(hash));
    }
}
