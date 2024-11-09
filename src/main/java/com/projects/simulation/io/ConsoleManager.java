package com.projects.simulation.io;

import com.projects.simulation.utils.MenuOptions;

import java.util.Scanner;

public class ConsoleManager {

    private final Scanner scanner;

    public ConsoleManager() {
        this.scanner = new Scanner(System.in);
    }

    public void printWelcomeWords() {
        System.out.println("____________________________________");
        System.out.println("Welcome to the life simulation game");
        System.out.println("The game has the following options:");
        System.out.println("____________________________________");
    }

    public void printGameFeatures() {
        for (MenuOptions options : MenuOptions.values()) {
            System.out.println(options.getOptionNumber() + ". " + options.getDescription());
        }
    }

    public void printInvalidOption() {

    }

    public int readUserInput() {
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

    public void printGoodByeWords() {
        System.out.println("____________________________________");
        System.out.println("Good by, see you soon!");
        System.out.println("____________________________________");
    }
}
