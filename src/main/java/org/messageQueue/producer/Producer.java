package org.messageQueue.producer;

import org.messageQueue.message.CustomMessageQueue;
import org.messageQueue.message.Message;

/**
 * Producer class responsible for generating and adding messages to a message queue.
 * This class implements the {@link Runnable} interface for execution in a thread pool.
 */
public class Producer implements Runnable {

    private final CustomMessageQueue queue;
    private final int messageCount;

    /**
     * Constructor for the Producer class.
     *
     * @param queue     the message queue to use for adding messages
     * @param messageCount the total number of messages to produce
     */
    public Producer(CustomMessageQueue queue, int messageCount) {
        this.queue = queue;
        this.messageCount = messageCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < messageCount; i++) {
            String content = "Message " + (i + 1);
            Message message = new Message(content);
            try {
                queue.offer(message);
                System.out.println("Produced message by producer : " + content);
            } catch (InterruptedException e) {
                System.out.println("Exception, while Add message in Queue : message is " + message +" Error message : "+ e.getMessage());
            }
        }
    }
}