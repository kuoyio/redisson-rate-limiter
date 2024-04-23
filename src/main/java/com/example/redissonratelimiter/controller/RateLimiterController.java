package com.example.redissonratelimiter.controller;

import com.example.redissonratelimiter.common.RedisKey;
import com.example.redissonratelimiter.controller.request.RateLimiterRequest;
import com.example.redissonratelimiter.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/rate-limiter")
@RequiredArgsConstructor
public class RateLimiterController {

    private final RedissonClient redissonClient;


    @GetMapping
    @RateLimiter(rate = 5)
    public void rateLimiter() {
        System.out.println("请求成功");
    }


    @PutMapping("/change")
    public void change(@RequestBody RateLimiterRequest rateLimiterRequest) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(RedisKey.RATE_LIMITER_PREFIX.getKey() + rateLimiterRequest.getMethodName());
        rateLimiter.expire(Duration.of(0, ChronoUnit.SECONDS));
        rateLimiter.trySetRate(RateType.OVERALL, rateLimiterRequest.getRate(), 1, RateIntervalUnit.SECONDS);
    }
}
