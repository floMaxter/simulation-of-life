package com.projects.simulation.utils;

import java.util.Optional;

public enum MenuOptions {
    OPTION_ONE_MOVE(1, "Make one move of the simulation"),
    OPTION_EXTINCTION_SIMULATION(2, "Start the final simulation"),
    OPTION_ENDLESS_SIMULATION(3, "Start an endless simulation cycle"),
    OPTION_PAUSE_SIMULATION(4, "Pause the simulation"),
    OPTION_NEW_MAP(5, "Create a new map"),
    OPTION_EXIT(6, "Exit the game");

    private final int optionNumber;
    private final String description;

    MenuOptions(int optionNumber, String description) {
        this.optionNumber = optionNumber;
        this.description = description;
    }

    public int getOptionNumber() {
        return optionNumber;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<MenuOptions> fromNumber(int number) {
        for (MenuOptions options : values()) {
            if (options.getOptionNumber() == number) {
                return Optional.of(options);
            }
        }
        return Optional.empty();
    }
}
