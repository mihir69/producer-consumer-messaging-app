package org.messageQueue.message;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Custom message queue implementation using an {@link ArrayDeque} for storing messages.
 * This queue offers thread-safe operations for adding (`offer`) and removing (`take`) messages
 * with bounded capacity.
 */
public class CustomMessageQueue {

    private final Queue<Message> queue;
    private final int capacity;

    static boolean flag = true;

    /**
     * Constructor for the CustomMessageQueue class.
     *
     * @param capacity the maximum number of messages the queue can hold (must be greater than 0)
     * @throws IllegalArgumentException if the specified capacity is less than or equal to 0
     */
    public CustomMessageQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Queue capacity must be greater than 0.");
        }
        this.queue = new ArrayDeque<>(capacity);
        this.capacity = capacity;
    }

    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }


    /**
     * add a message to the message queue.
     * @param message the message to add to the queue
     * @return true if the message was successfully added, false otherwise
     * @throws InterruptedException if the thread is interrupted while waiting for space in the queue
     */
    public synchronized boolean offer(Message message) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        if (queue.offer(message)) {
            notifyAll();
            return true;
        }
        return false;
    }


    /**
     * Retrieves and removes a message from the message queue.
     * @return the message retrieved from the queue, or null if the queue is empty
     * @throws InterruptedException if the thread is interrupted while waiting for a message
     */
    public synchronized Message take() throws InterruptedException {

      // for verify error count while message processing
        if(flag){
            flag = false;
            throw new InterruptedException("MANUAL/DUMMY EXCEPTION : UT : EXCEPTION WHILE CONSUMER REMOVE PROCESS QUEUE");
        }

        while (queue.isEmpty()) {
            wait();
        }
        Message message = queue.poll();
        notifyAll();
        return message;
    }
}
