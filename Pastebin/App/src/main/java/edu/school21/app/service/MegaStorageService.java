package edu.school21.app.service;

import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.cmd.MegaCmdGet;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MegaStorageService {

    private MegaSession megaSession;

    public MegaStorageService(MegaSession megaSession) {
        this.megaSession = megaSession;
    }

    public void upload(File fileForSave) {
        megaSession.uploadFile(fileForSave.getPath())
                .createRemotePathIfNotPresent()
                .run();
    }

    public void delete(String remotePath) {
        megaSession.remove(remotePath).run();
    }

    public File read(String remotePath, String localDestination) {
        MegaCmdGet file = megaSession.get(remotePath);
        file.setLocalPath(localDestination).run();
        return new File(localDestination);
    }
}
