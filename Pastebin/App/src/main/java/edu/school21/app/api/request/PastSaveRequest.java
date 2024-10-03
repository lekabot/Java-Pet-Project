package edu.school21.app.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request for save text")
public class PastSaveRequest {
    @Schema(description = "Text for save", example = "Hello world!")
    private String data;
    @Schema(description = "Time to life in seconds", example = "3600")
    private Long expirationTimeSeconds;
}
