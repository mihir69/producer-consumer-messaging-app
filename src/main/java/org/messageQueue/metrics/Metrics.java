package org.messageQueue.metrics;

import java.util.concurrent.atomic.AtomicInteger;

public class Metrics {

    private final AtomicInteger successCount = new AtomicInteger(0);
    private final AtomicInteger errorCount = new AtomicInteger(0);

    public void incrementSuccessCount() {
        successCount.incrementAndGet(); // Atomically increment and get
    }

    public void incrementErrorCount() {
        errorCount.incrementAndGet();
    }

    public int getSuccessCount() {
        return successCount.get(); // Get the current value
    }

    public int getErrorCount() {
        return errorCount.get();
    }
}
