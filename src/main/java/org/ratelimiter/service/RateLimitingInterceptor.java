package org.ratelimiter.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ratelimiter.RateLimitingStrategy;
import org.ratelimiter.config.RateLimiterConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

@Service
public class RateLimitingInterceptor implements HandlerInterceptor {
    private final RateLimiterConfig rateLimiterConfig;

    RateLimitingInterceptor(RateLimiterConfig rateLimiterConfig) {
        this.rateLimiterConfig = rateLimiterConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("X-User-Id");
        boolean isAllowed = rateLimiterConfig.getActiveStrategy().allowRequest(userId);
        if(!isAllowed) {
            response.setStatus(429);
            response.getWriter().write("Rate limit exceeded");
            response.setHeader("Retry after", "1");
            return false;
        }
        return true;
    }
}
