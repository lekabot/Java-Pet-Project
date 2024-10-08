package edu.school21.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Profile("!test")
@Configuration
public class RedisHashConfig {

    @Value("${spring.redis.hash.host}")
    private String redisHashHost;

    @Value("${spring.redis.hash.port}")
    private int redisHashPort;

    @Bean(name = "redisConnectionFactoryForHash")
    public RedisConnectionFactory redisConnectionFactoryForHash() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHashHost, redisHashPort));
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactoryForHash());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
