package edu.school21.app.config.TestsConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
@Profile("test")
public class RedisHashTestConfig {

    private static final GenericContainer<?> redisHashContainer =
            new GenericContainer<>(DockerImageName.parse("redis:6.2.6"))
                    .withExposedPorts(6379);

    static {
        redisHashContainer.start();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactoryForHashTest() {
        return new LettuceConnectionFactory(redisHashContainer.getHost(), redisHashContainer.getMappedPort(6379));
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateForHashTest() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactoryForHashTest());
        return template;
    }
}