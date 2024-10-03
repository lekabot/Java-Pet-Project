package edu.school21.app.service;

import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.cmd.MegaCmdGet;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * This class is not working because library for working with Mega has deprecated.
 *
 * @author Lopatin Ilya
 * @version 1.0
 */
@Service
public class MegaStorageService implements RemoteStorageService {

    private final MegaSession megaSession;

    public MegaStorageService(MegaSession megaSession) {
        this.megaSession = megaSession;
    }

    public void uploadFileInStorage(File fileForSave) {
        megaSession.uploadFile(fileForSave.getPath())
                .createRemotePathIfNotPresent()
                .run();
    }

    public void readInFile(String remotePath, String localDestination) {
        MegaCmdGet file = megaSession.get(remotePath);
        file.setLocalPath(localDestination).run();
    }

    @Override
    public String read(String hash) {
        String localFilePath = "src/main/resources/" + hash;
        try {
            readInFile(hash, localFilePath);
            return Files.readString(Path.of(localFilePath));
        } catch (IOException e) {
            throw new RuntimeException("File read error ", e);
        } finally {
            new File(localFilePath).delete();
        }
    }

    @Override
    public void upload(String hash, String data) {
        File file = new File("src/main/resources/" + hash);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
            writer.flush();
            uploadFileInStorage(file);
        } catch (IOException e) {
            throw new RuntimeException("File load error ", e);
        }
    }

    @Override
    public void delete(String hash) {
        megaSession.remove(hash).run();
    }
}
