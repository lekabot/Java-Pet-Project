package edu.school21.app.controllers;

import edu.school21.app.api.request.PastSaveRequest;
import edu.school21.app.service.PastService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Text Controllers")
@RequestMapping("api/v1/past")
public class PastController {
    private final PastService pastService;

    @Autowired
    public PastController(PastService pastService) {
        this.pastService = pastService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createText(@RequestBody PastSaveRequest request) {
        String result = pastService.saveText(request.getData(), request.getExpirationTimeSeconds());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<String> getText(@PathVariable String hash) {
        try {
            String text = pastService.getTextByHash(hash);
            return ResponseEntity.ok(text);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hash does not exist");
        }
    }

    @DeleteMapping("/delete/{hash}")
    public ResponseEntity<String> deleteText(@PathVariable String hash) {
        if (pastService.getPastByHash(hash).isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hash does not exist");
        }
        pastService.deleteTextByHash(hash);
        return ResponseEntity.ok("Text has been successfully deleted");
    }
}
