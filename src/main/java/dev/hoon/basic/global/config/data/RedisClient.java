package dev.hoon.basic.global.config.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hoon.basic.global.model.JwtExpired;
import lombok.Getter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Getter
public class RedisClient {

    private RedisTemplate<String, String>     redisTemplate  = new RedisTemplate<>();
    private RedisTemplate<String, JwtExpired> entityTemplate = new RedisTemplate<>(); // TODO : Modify Generic Type

    private RedisClient() {

    }

    public void addDefaultSerializer(RedisConnectionFactory redisConnectionFactory, ObjectMapper ObjectMapper) {

        Jackson2JsonRedisSerializer<JwtExpired> serializer = new Jackson2JsonRedisSerializer<>(JwtExpired.class);

        serializer.setObjectMapper(ObjectMapper);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();

        entityTemplate.setKeySerializer(new StringRedisSerializer());
        entityTemplate.setHashValueSerializer(serializer);
        entityTemplate.setConnectionFactory(redisConnectionFactory);
        entityTemplate.afterPropertiesSet();
    }

    public static class Instance {

        private static RedisClient REDIS_CLIENT = new RedisClient();

        public static RedisClient getInstance() {

            return REDIS_CLIENT;
        }
    }

}