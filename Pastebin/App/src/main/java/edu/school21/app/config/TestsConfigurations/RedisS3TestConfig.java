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
public class RedisS3TestConfig {

    private static final int REDIS_PORT = 6379;
    private static final GenericContainer<?> redisS3Container;

    static {
        redisS3Container = new GenericContainer<>(DockerImageName.parse("redis:6.2.6"))
                .withExposedPorts(REDIS_PORT);
        redisS3Container.start();
    }

    private RedisConnectionFactory createConnectionFactoryTest() {
        return new LettuceConnectionFactory(redisS3Container.getHost(), redisS3Container.getMappedPort(REDIS_PORT));
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateForS3Test() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(createConnectionFactoryTest());
        return template;
    }
}