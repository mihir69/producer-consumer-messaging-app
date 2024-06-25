package org.messageQueue.executorConfig;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class ConsumerThreadPoolExecutor extends ThreadPoolExecutor {

    public ConsumerThreadPoolExecutor(ThreadPoolConfig config) {
        super(config.getCorePoolSize(), config.getMaxPoolSize(), config.getKeepAliveTime(), config.getKeepAliveTimeUnit(),
                new LinkedBlockingQueue<>());
    }
}
