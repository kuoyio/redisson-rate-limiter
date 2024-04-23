package com.example.redissonratelimiter.controller.request;

import lombok.Data;

@Data
public class RateLimiterRequest {
    private long rate;
    private String methodName;
}
