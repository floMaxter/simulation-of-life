package com.projects.simulation.io;

import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.WorldMap;
import com.projects.simulation.utils.MenuOptions;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ConsoleWriter {

    private final BlockingQueue<Integer> queue;

    private static final Scanner scanner = new Scanner(System.in);

    public ConsoleWriter(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public static void printWelcomeWords() {
        System.out.println("____________________________________");
        System.out.println("Welcome to the life simulation game!");
        System.out.println("____________________________________");
    }

    public static void printGameFeatures() {
        System.out.println("____________________________________");
        for (MenuOptions options : MenuOptions.values()) {
            System.out.println(options.getOptionNumber() + ". " + options.getDescription());
        }
        System.out.println("____________________________________");
    }

    public static void printNumberOfEntities(WorldMap worldMap) {
        System.out.println("____________________________________");
        for (EntityType entityType : EntityType.values()) {
            if (entityType.equals(EntityType.GROUND)) continue;
            System.out.println(entityType.getName() + ": " + worldMap.getNumberOfEntity(entityType));
        }
        System.out.print("____________________________________");
    }

    public static void printGoodByeWords() {
        System.out.println("____________________________________");
        System.out.println("Good by, see you soon!");
        System.out.println("____________________________________");
    }

    public static void printMessage(String message) {
        System.out.println("____________________________________");
        System.out.println(message);
        System.out.println("____________________________________");
    }

    public static int readUserInput() {
        int input;
        while (true) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                break;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return input;
    }
}
