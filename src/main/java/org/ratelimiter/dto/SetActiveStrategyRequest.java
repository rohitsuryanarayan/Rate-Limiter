package org.ratelimiter.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SetActiveStrategyRequest {
    String strategy;
}
