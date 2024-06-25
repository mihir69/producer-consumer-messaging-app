package org.messageQueue.executorConfig;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class ProducerThreadPoolExecutor extends ThreadPoolExecutor {

    public ProducerThreadPoolExecutor(ThreadPoolConfig config) {
        super(config.getCorePoolSize(), config.getMaxPoolSize(), config.getKeepAliveTime(), config.getKeepAliveTimeUnit(),
                new LinkedBlockingQueue<>());
    }
}
