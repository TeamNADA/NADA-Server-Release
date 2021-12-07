package com.nada.server.commons;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public Optional<String> getData(String key){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return Optional.ofNullable(valueOperations.get(key));
    }

    public void setData(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setDateExpire(String key, String value, long duration){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }

}
