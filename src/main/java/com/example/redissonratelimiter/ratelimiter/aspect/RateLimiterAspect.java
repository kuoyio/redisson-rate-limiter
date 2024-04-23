package com.example.redissonratelimiter.ratelimiter.aspect;

import com.example.redissonratelimiter.common.RedisKey;
import com.example.redissonratelimiter.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RateLimiterAspect {

    private final RedissonClient redissonClient;

    @Around("@annotation(rateLimiterAnnotation)")
    public Object beforeAnnotation(ProceedingJoinPoint joinPoint, RateLimiter rateLimiterAnnotation) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(RedisKey.RATE_LIMITER_PREFIX.getKey() + methodName);
        if (!rateLimiter.isExists()) {
            rateLimiter.trySetRate(RateType.OVERALL, rateLimiterAnnotation.rate(), rateLimiterAnnotation.rateInterval(), rateLimiterAnnotation.rateIntervalUnit());
        }
        if (!rateLimiter.tryAcquire()) {
            log.error("请求失败");
            return null;
        }
        return joinPoint.proceed();
    }
}
