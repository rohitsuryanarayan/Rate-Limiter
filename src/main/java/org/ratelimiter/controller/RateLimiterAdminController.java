package org.ratelimiter.controller;

import org.ratelimiter.config.RateLimiterConfig;
import org.ratelimiter.dto.SetActiveStrategyRequest;
import org.ratelimiter.service.StrategyRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RateLimiterAdminController {
    private final RateLimiterConfig rateLimiterConfig;
    private final StrategyRegistry strategyRegistry;

    @Autowired
    public RateLimiterAdminController(StrategyRegistry strategyRegistry,
                                      RateLimiterConfig rateLimiterConfig) {
        this.strategyRegistry = strategyRegistry;
        this.rateLimiterConfig = rateLimiterConfig;
    }

    @GetMapping("/strategies")
    public List<String> getAllStrategies() {
        return new ArrayList<>(strategyRegistry.getStrategies());
    }

    @GetMapping("/activestrategy")
    public String getActiveStrategy() {return rateLimiterConfig.getActiveStrategy().getName();}

    @PostMapping("/activestrategy")
    public void setActiveStrategy(@RequestBody SetActiveStrategyRequest request) {
        rateLimiterConfig.setActiveStrategy(request.getStrategy());
    }
}
