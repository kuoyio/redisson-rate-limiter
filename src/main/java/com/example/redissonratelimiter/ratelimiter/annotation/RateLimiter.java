package com.example.redissonratelimiter.ratelimiter.annotation;

import org.redisson.api.RateIntervalUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {
    long rate() default 1L;

    long rateInterval() default 1L;

    RateIntervalUnit rateIntervalUnit() default RateIntervalUnit.SECONDS;
}
