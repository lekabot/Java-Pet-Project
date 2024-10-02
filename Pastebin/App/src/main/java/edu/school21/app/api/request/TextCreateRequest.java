package edu.school21.app.api.request;

import lombok.Data;

@Data
public class TextCreateRequest {
    private String data;
    private Long expirationTimeSeconds;
    private TextStatus status;
}
