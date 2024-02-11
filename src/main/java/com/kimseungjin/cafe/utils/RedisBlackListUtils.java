package com.kimseungjin.cafe.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisBlackListUtils implements BlackListUtils {

    private static final long ACCESS_TOKEN_EXPIRE_HOUR = 1L;
    private final RedisTemplate<String, String> redisBlackListTemplate;

    public RedisBlackListUtils(final RedisTemplate<String, String> redisBlackListTemplate) {
        this.redisBlackListTemplate = redisBlackListTemplate;
    }

    @Override
    public void add(final String accessToken, final UUID memberId) {
        redisBlackListTemplate
                .opsForValue()
                .set(accessToken, memberId.toString(), ACCESS_TOKEN_EXPIRE_HOUR, TimeUnit.HOURS);
    }

    @Override
    public boolean hasKey(final String accessToken) {
        return Optional.ofNullable(redisBlackListTemplate.hasKey(accessToken)).orElse(true);
    }
}
