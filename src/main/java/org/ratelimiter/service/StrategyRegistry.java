package org.ratelimiter.service;

import org.ratelimiter.strategy.RateLimitingStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StrategyRegistry {
    private final Map<String, RateLimitingStrategy> strategies = new HashMap<>();

    public StrategyRegistry(List<RateLimitingStrategy> strategyList) {
        for(RateLimitingStrategy strategy: strategyList) {
            strategies.put(strategy.getName(), strategy);
        }
    }

    public Set<String> getStrategies() {
        return strategies.keySet();
    }

    public RateLimitingStrategy getStrategyByName(String name) {
        return strategies.get(name);
    }
}
