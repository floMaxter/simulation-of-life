package com.projects.simulation.io;

import com.projects.simulation.GameUtils;

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
        System.out.println("1. Make one move of the simulation");
        System.out.println("2. Start an endless simulation cycle");
        System.out.println("3. Pause the simulation");
        System.out.println("4. Create a new map");
        System.out.println("5. Exit the game");
    }

    public int readUserInput() {
        int input;
        while (true) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (isInvalidInput(input)) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return input;
    }

    private boolean isInvalidInput(int input) {
        return input < GameUtils.OPTION_ONE_MOVE || input > GameUtils.OPTION_EXIT;
    }

    public void printGoodByeWords() {
        System.out.println("____________________________________");
        System.out.println("Good by, see you soon!");
        System.out.println("____________________________________");
    }
}
