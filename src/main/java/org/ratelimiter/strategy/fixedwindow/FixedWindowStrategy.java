package org.ratelimiter.strategy.fixedwindow;

import org.ratelimiter.strategy.RateLimitingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FixedWindowStrategy implements RateLimitingStrategy {
    private final int maxRequestsPerWindow;
    private final long windowSizeInMillis;

    private final Map<String, Window> windows = new HashMap<>();

    FixedWindowStrategy(@Value("${ratelimiter.fixed.max-requests:1}") int maxRequestsPerWindow,
                        @Value("${ratelimiter.fixed.window-ms:1000}") int windowSizeInMillis) {
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    @Override
    public boolean allowRequest(String client_id) {
        long currentWindow = System.currentTimeMillis() / windowSizeInMillis;
        if(windows.containsKey(client_id)) {
            Window w = windows.get(client_id);
            if(w.getCount() == maxRequestsPerWindow)    return false;
            w.setCount(w.getCount()+1);
        }
        else {
            Window w = new Window(currentWindow, 1);
            windows.put(client_id, w);
        }
        return true;
    }
    @Override
    public String getName() {
        return "fixed-window";
    }
}
