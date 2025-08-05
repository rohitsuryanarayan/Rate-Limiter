package org.ratelimiter.strategy.slidingwindow;

import lombok.AllArgsConstructor;
import org.ratelimiter.strategy.RateLimitingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SlidingWindowStrategy implements RateLimitingStrategy {
    private final int maxRequests;
    private final long windowSizeInMillis;
    private final Map<String, Deque<Long>> logs = new ConcurrentHashMap<>();

    SlidingWindowStrategy(@Value("${ratelimiter.fixed.max-requests:1}") int maxRequestsPerWindow,
                          @Value("${ratelimiter.fixed.window-ms:100000}") int windowSizeInMillis) {
        this.maxRequests = maxRequestsPerWindow;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    @Override
    public boolean allowRequest(String client_id) {
        long now = System.currentTimeMillis();

        Deque<Long> deque = logs.computeIfAbsent(client_id, k->new ArrayDeque<>());

        synchronized (deque) {
            long boundary = now - windowSizeInMillis;
            while(!deque.isEmpty() && deque.peekFirst() < boundary) {
                deque.pollFirst();
            }
            if(deque.size() < maxRequests) {
                deque.addLast(now);
                return true;
            }
            return false;
        }
    }

    @Override
    public String getName() {
        return "sliding-window";
    }
}
