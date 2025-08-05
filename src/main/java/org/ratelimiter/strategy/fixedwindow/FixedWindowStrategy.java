package org.ratelimiter.strategy.fixedwindow;

import org.ratelimiter.strategy.RateLimitingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class FixedWindowStrategy implements RateLimitingStrategy {
    private final int maxRequestsPerWindow;
    private final long windowSizeInMillis;

    private final Map<String, Window> windows = new ConcurrentHashMap<>();

    FixedWindowStrategy(@Value("${ratelimiter.fixed.max-requests:1}") int maxRequestsPerWindow,
                        @Value("${ratelimiter.fixed.window-ms:10000}") int windowSizeInMillis) {
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    @Override
    public boolean allowRequest(String client_id) {
        long currentWindow = System.currentTimeMillis() / windowSizeInMillis;
        AtomicBoolean allowed = new AtomicBoolean(true);
        windows.compute(client_id, (key,window)-> {
            if(window == null || window.getWindowId() != currentWindow) {
                return new Window(currentWindow, 1);
            }
            if(window.getCount() < maxRequestsPerWindow) {
                allowed.set(true);
                window.incrementCount();
            }
            else {
                allowed.set(false);
            }
            return window;
        });
        return allowed.get();
    }
    @Override
    public String getName() {
        return "fixed-window";
    }
}
