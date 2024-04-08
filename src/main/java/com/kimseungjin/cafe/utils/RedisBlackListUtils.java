package com.kimseungjin.cafe.utils;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisBlackListUtils implements BlackListUtils {

    private static final long ACCESS_TOKEN_EXPIRE_HOUR = 1L;
    private static final String EMPTY_VALUE = "";
    private final RedisTemplate<String, String> redisBlackListTemplate;

    @Override
    public void add(final String accessToken) {
        redisBlackListTemplate
                .opsForValue()
                .set(accessToken, EMPTY_VALUE, ACCESS_TOKEN_EXPIRE_HOUR, TimeUnit.HOURS);
    }

    @Override
    public boolean hasKey(final String accessToken) {
        return Optional.ofNullable(redisBlackListTemplate.hasKey(accessToken)).orElse(true);
    }
}
