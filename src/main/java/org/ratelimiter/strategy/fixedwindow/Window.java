package org.ratelimiter.strategy.fixedwindow;

import lombok.Getter;
import lombok.Setter;

public class Window {
    @Getter
    private long windowId;
    @Getter
    @Setter
    private int count;

    Window(long windowId, int count) {
        this.windowId = windowId;
        this.count = count;
    }
}
