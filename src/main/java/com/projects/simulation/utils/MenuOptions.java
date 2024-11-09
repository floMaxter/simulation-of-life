package com.projects.simulation.utils;

import java.util.Arrays;
import java.util.Optional;

public enum MenuOptions {
    OPTION_ONE_MOVE(1, "Make one move of the simulation"),
    OPTION_ENDLESS_CYCLE(2, "Start an endless simulation cycle"),
    OPTION_PAUSE_SIMULATION(3, "Pause the simulation"),
    OPTION_NEW_MAP(4, "Create a new map"),
    OPTION_EXIT(5, "Exit the game");

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

    public static Integer getMinNumberValue() {
        return Arrays.stream(MenuOptions.values())
                .mapToInt(MenuOptions::getOptionNumber)
                .min()
                .orElseThrow(() -> new IllegalStateException("Enum is empty"));
    }

    public static Integer getMaxNumberValue() {
        return Arrays.stream(MenuOptions.values())
                .mapToInt(MenuOptions::getOptionNumber)
                .max()
                .orElseThrow(() -> new IllegalStateException("Enum is empty"));
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
