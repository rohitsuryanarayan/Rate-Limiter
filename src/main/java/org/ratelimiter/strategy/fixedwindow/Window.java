package org.ratelimiter.strategy.fixedwindow;

import lombok.Getter;
import lombok.Setter;

public class Window {
    @Getter
    private final long windowId;
    private int count;

    Window(long windowId, int initialCount) {
        this.windowId = windowId;
        this.count = initialCount;
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized void incrementCount() {
        count++;
    }
}
