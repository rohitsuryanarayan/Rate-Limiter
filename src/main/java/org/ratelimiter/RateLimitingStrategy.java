package org.ratelimiter;

import org.springframework.stereotype.Component;

@Component
public interface RateLimitingStrategy {
    boolean allowRequest(String client_id);
    String getName();
}
