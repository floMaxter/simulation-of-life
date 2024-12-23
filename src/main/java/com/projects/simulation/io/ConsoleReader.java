package com.projects.simulation.io;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ConsoleReader implements Runnable {

    private final BlockingQueue<Integer> queue;
    private static final Scanner scanner = new Scanner(System.in);

    public ConsoleReader(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int input;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (scanner.hasNextInt()) {
                    input = scanner.nextInt();
                    queue.put(input);
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
