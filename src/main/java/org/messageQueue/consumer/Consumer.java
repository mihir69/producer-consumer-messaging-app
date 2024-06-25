package org.messageQueue.consumer;


import org.messageQueue.message.CustomMessageQueue;
import org.messageQueue.message.Message;
import org.messageQueue.metrics.Metrics;

/**
 * Consumer class responsible for retrieving and processing messages from a message queue.
 * This class implements the {@link Runnable} interface for execution in a thread pool.
 */
public class Consumer implements Runnable {

    private final CustomMessageQueue queue;
    private final Metrics metrics;


    /**
     * Constructor for the Consumer class.
     *
     * @param queue  the message queue to use for consuming messages
     * @param metrics the {@link Metrics} object to update with processing results
     */
    public Consumer(CustomMessageQueue queue, Metrics metrics) {
        this.queue = queue;
        this.metrics = metrics;
    }

    /**
     continuously processing messages
      */
    @Override
    public void run() {
        while (true) {
            try {
                Message message = queue.take();
                System.out.println("Processed by consumer : "+ message.getContent());
                metrics.incrementSuccessCount();
            }
            catch (Exception e) {
                metrics.incrementErrorCount();
                System.err.println("Error processing message: " + e.getMessage());
            }
        }
    }

}

