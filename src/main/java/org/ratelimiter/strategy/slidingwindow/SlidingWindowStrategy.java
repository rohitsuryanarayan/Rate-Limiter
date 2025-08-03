package org.ratelimiter.strategy.slidingwindow;

import org.ratelimiter.strategy.RateLimitingStrategy;
import org.springframework.stereotype.Component;

@Component
public class SlidingWindowStrategy implements RateLimitingStrategy {
    @Override
    public boolean allowRequest(String client_id) {
        return true;
    }
    @Override
    public String getName() {
        return "sliding-window";
    }
}
