package com.example.redissonratelimiter.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKey {

    RATE_LIMITER_PREFIX("RATE_LIMITER:");

    private final String key;
}
