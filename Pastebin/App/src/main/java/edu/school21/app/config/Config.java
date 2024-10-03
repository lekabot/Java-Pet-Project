package edu.school21.app.config;

import io.github.eliux.mega.Mega;
import io.github.eliux.mega.MegaSession;
import io.github.eliux.mega.auth.MegaAuthCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {

    @Bean
    public MegaSession megaSession() {
        return Mega.login(MegaAuthCredentials.createFromEnvVariables());
    }
}
