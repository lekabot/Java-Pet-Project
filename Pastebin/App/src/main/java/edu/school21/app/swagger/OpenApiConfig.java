package edu.school21.app.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Pastebin API",
                description = "Pastebin", version = "1.0.0",
                contact = @Contact(
                        name = "Lopatin Ilya",
                        email = "mrmochin@yandex.ru"
                )
        )
)
public class OpenApiConfig {
}
