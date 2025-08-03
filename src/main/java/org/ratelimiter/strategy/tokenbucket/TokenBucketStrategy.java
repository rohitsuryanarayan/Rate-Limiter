package org.ratelimiter.strategy.tokenbucket;

import org.ratelimiter.strategy.RateLimitingStrategy;
import org.springframework.stereotype.Component;

@Component
public class TokenBucketStrategy implements RateLimitingStrategy {
    @Override
    public boolean allowRequest(String client_id) {
        return false;
    }

    @Override
    public String getName() {
        return "token-bucket";
    }
}
