package org.messageQueue;

import org.messageQueue.consumer.Consumer;
import org.messageQueue.exception.InvalidMessageCountException;
import org.messageQueue.executorConfig.ConsumerThreadPoolExecutor;
import org.messageQueue.executorConfig.ProducerThreadPoolExecutor;
import org.messageQueue.executorConfig.ThreadPoolConfig;
import org.messageQueue.message.CustomMessageQueue;
import org.messageQueue.metrics.Metrics;
import org.messageQueue.producer.Producer;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main class (entry point) for message queue system, coordinates producers, consumers, and thread pools.
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {
        int messageCount = getMessageCountFromUser();
        int queueCapacity = 10;

        int producerTasks = 1;
        int consumerTasks = 1;

        CustomMessageQueue queue = new CustomMessageQueue(queueCapacity);
        Metrics metrics = new Metrics();

        // Configure thread pools
        ThreadPoolConfig producerConfig = new ThreadPoolConfig(1, 1, 60, TimeUnit.SECONDS);
        ThreadPoolConfig consumerConfig = new ThreadPoolConfig(1, 1, 60, TimeUnit.SECONDS);

        // Create thread pool executors
        ExecutorService producerExecutor = new ProducerThreadPoolExecutor(producerConfig);
        ExecutorService consumerExecutor = new ConsumerThreadPoolExecutor(consumerConfig);

        Consumer consumerTask = new Consumer(queue, metrics);
        Producer producerTask = new Producer(queue, messageCount);


        // submit tasks to Executors
        for(int i = 0 ; i<producerTasks ; i++){
            producerExecutor.submit(producerTask);
        }

        for(int i = 0 ; i<consumerTasks ; i++){
            consumerExecutor.submit(consumerTask);
        }


//         Wait for producer to finish
        producerExecutor.shutdown();
        while (!producerExecutor.isTerminated()) {
            Thread.sleep(100);
        }

        // Interrupt consumers after producer finishes
        consumerExecutor.shutdownNow();


        System.out.println("******************************* Processing completed. Metrics: **************************************");
        System.out.println("                                                                                                     ");
        System.out.println("                Successfully processed message count: " + metrics.getSuccessCount());
        System.out.println("                Error while processing message count: " + metrics.getErrorCount());
        System.out.println("                                                                                                     ");
        System.out.println("*****************************************************************************************************");
    }


    /**
     * Prompts user for message count (positive integer), validates input, handles exceptions.
     * @return number of messages to produce entered by user
     */

    public static int getMessageCountFromUser() {
        Scanner scanner = new Scanner(System.in);
        int messageCount;

        while (true) {
            try {
                System.out.print("Enter the number of messages to produce (must be greater than 0): ");
                messageCount = scanner.nextInt();

                // Validate message count
                if (messageCount <= 0) {
                    throw new InvalidMessageCountException("Message count must be greater than 0.");
                }
                break; // Exit the loop on valid input
            } catch (InputMismatchException e) {
                System.err.println("Error: Invalid input. Please enter an integer value.");
                scanner.nextLine(); // Clear the buffer to avoid issues in next iteration
            } catch (InvalidMessageCountException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: unexpected exception during user input " + e.getStackTrace() + " Error message "+ e.getMessage());
            }
        }

        scanner.close();
        return messageCount;
    }

}