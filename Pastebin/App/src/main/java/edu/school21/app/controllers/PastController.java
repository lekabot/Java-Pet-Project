package edu.school21.app.controllers;

import edu.school21.app.api.request.PastSaveRequest;
import edu.school21.app.service.PastService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Text Controllers")
@RequestMapping("api/v1/past")
public class PastController {
    private final PastService pastService;

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
        String text = pastService.getTextByHash();
        return ResponseEntity.ok(text);
    }

    @DeleteMapping("/delete/{hash}")
    public ResponseEntity<String> deleteText(@PathVariable String hash) {
        pastService.deleteTextByHash(hash);
        return ResponseEntity.ok("Text has been successfully deleted");
    }
}
