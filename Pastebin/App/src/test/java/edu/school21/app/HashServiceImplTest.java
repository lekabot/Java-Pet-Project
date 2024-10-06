package edu.school21.app;

import edu.school21.app.models.hash.HashEntity;
import edu.school21.app.repository.hash.HashRepository;
import edu.school21.app.service.HashService;
import edu.school21.app.service.HashServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@ActiveProfiles("test")
public class HashServiceImplTest {
    private HashService hashService;

    private HashRepository hashRepository;

    @Autowired
    public HashServiceImplTest(HashService hashService, HashRepository hashRepository) {
        this.hashService = hashService;
        this.hashRepository = hashRepository;
    }

    @BeforeEach
    public void setUp() {
        hashService = new HashServiceImpl(hashRepository);
    }

    @Test
    public void testCrateHash() {
        String hash = hashService.createHash();
        assertNotNull(hash);
        assertEquals(8, hash.length());
    }

    @Test
    public void testSaveHash() {
        String hash = hashService.createHash();
        hashService.saveHash(hash);

        HashEntity savedHash = hashRepository.findByHash(hash).orElse(null);
        assertNotNull(savedHash);
        assertEquals(hash, savedHash.getHash());
    }

    @Test
    public void testReadHash() {
        String hash = hashService.createHash();
        hashService.saveHash(hash);

        String retrievedHash = hashService.readHash(hash);
        assertEquals(hash, retrievedHash);
    }

    @Test
    public void testIsNotExist() {
        String hash = hashService.createHash();
        assertTrue(hashService.isNotExist(hash));

        hashService.saveHash(hash);
        assertFalse(hashService.isNotExist(hash));
    }


    @Test
    @Transactional
    public void testDeleteHash() {
        String hash = hashService.createHash();
        hashService.saveHash(hash);

        assertFalse(hashService.isNotExist(hash));

        hashService.deleteHash(hash);

        assertTrue(hashService.isNotExist(hash));
    }

}
