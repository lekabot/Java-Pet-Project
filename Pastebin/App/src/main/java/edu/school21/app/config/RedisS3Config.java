package edu.school21.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
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
public class RedisS3Config {

    @Value("${spring.redis.s3.host}")
    private String redisS3Host;

    @Value("${spring.redis.s3.port}")
    private int redisS3Port;

    @Bean(name = "redisConnectionFactoryForS3")
    public RedisConnectionFactory redisConnectionFactoryForS3() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisS3Host, redisS3Port));
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateForS3() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactoryForS3());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
