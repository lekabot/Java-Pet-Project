package edu.school21.app;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import edu.school21.app.config.YandexCloudConfig;
import edu.school21.app.service.RemoteStorageService;
import edu.school21.app.service.YandexCloudStorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {YandexCloudStorageService.class, YandexCloudConfig.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ActiveProfiles("test")
public class YandexCloudStorageServiceTest {

    @Autowired
    private RemoteStorageService storageService;

    @Autowired
    private AmazonS3 s3Client;

    private final String testHash = "testHash";
    private final String testData = "Hello, Yandex Cloud!";

    @BeforeEach
    public void setUp() {
        storageService.upload(testHash, testData);
    }

    @AfterEach
    public void tearDown() {
        storageService.delete(testHash);
    }

    @Test
    public void testUpload() {
        S3Object s3Object = s3Client.getObject("mypastebinbacket", testHash);
        String retrievedData;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8))) {
            retrievedData = reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Error reading object", e);
        }

        assertEquals(testData, retrievedData);
    }

    @Test
    public void testRead() {
        String retrievedData = storageService.read(testHash);
        assertEquals(testData, retrievedData);
    }

    @Test
    public void testDelete() {
        storageService.delete(testHash);

        assertThrows(Exception.class, () -> {
            s3Client.getObject("mypastebinbacket", testHash);
        });
    }

    @Test
    public void testDeleteAll() {
        String testHash1 = "testHash1";
        String testData1 = "Hello, Yandex Cloud 1!";

        String testHash2 = "testHash2";
        String testData2 = "Hello, Yandex Cloud 2!";

        storageService.upload(testHash1, testData1);
        storageService.upload(testHash2, testData2);

        storageService.deleteAll();

        assertThrows(Exception.class, () -> {
            s3Client.getObject("mypastebinbacket", testHash1);
        });

        assertThrows(Exception.class, () -> {
            s3Client.getObject("mypastebinbacket", testHash2);
        });
    }
}
