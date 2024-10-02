package edu.school21.app.controllers;

import edu.school21.app.api.request.TextCreateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
public class Controller {
    @GetMapping("/v1/{hash}")
    public String getTextByHash(@PathVariable String hash) {
        return hash;
    }

    @GetMapping("/v1/")
    public Collection<String> getPublicPasteList() {
        return Collections.emptyList();
    }

    @PostMapping("/v1/crate_text")
    public String addText(
            @RequestBody TextCreateRequest request) {
        return request.getData();
    }

}
