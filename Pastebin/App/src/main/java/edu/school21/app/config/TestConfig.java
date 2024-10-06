package edu.school21.app.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration

public class TestConfig {
    @Bean
    @Profile("test")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:testdb")
                .username("admin")
                .password("admin")
                .driverClassName("org.h2.Driver")
                .build();
    }
}