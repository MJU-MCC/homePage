package com.example.mccHomePage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redistPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(redisHost, redistPort);
    }

    @Bean
    public RedisTemplate<String , String> redisTemplate(){

        /**
         * RedisTemplate<String, String> redistemplate = new RedisTemplate<>();
         *         redistemplate.setConnectionFactory(redisConnectionFactory());
         *         redistemplate.setKeySerializer(new StringRedisSerializer());
         *         redistemplate.setValueSerializer(new StringRedisSerializer());
         *
         *         이렇게 하지 않은 이유는 Redis 라이브러리 중에서 키와 값을 String으로 직렬화 해주는것이
         *         StringRedisTemplate 타입에 내장되어 있기 때문에 가능하다.
         */
        StringRedisTemplate redisT = new StringRedisTemplate();
        redisT.setConnectionFactory(redisConnectionFactory());


        return redisT;
    }
}
