package org.messageQueue.executorConfig;

import java.util.concurrent.TimeUnit;

public class ThreadPoolConfig {

    private final int corePoolSize;
    private final int maxPoolSize;
    private final long keepAliveTime;
    private final TimeUnit keepAliveTimeUnit;

    public ThreadPoolConfig(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit keepAliveTimeUnit) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.keepAliveTimeUnit = keepAliveTimeUnit;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public TimeUnit getKeepAliveTimeUnit() {
        return keepAliveTimeUnit;
    }
}
