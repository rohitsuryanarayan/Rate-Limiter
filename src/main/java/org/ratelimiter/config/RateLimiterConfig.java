package org.ratelimiter.config;

import lombok.Setter;
import org.ratelimiter.RateLimitingStrategy;
import org.ratelimiter.service.StrategyRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterConfig {
    @Setter
    private String activeStrategy;
    private final StrategyRegistry strategyRegistry;

    RateLimiterConfig(@Value("${ratelimiter.strategy:fixed-window}") String strategy,
    StrategyRegistry strategyRegistry) {
        this.activeStrategy = strategy;
        this.strategyRegistry = strategyRegistry;
    }

    public RateLimitingStrategy getActiveStrategy() {
        return strategyRegistry.getStrategyByName(activeStrategy);
    }

}
