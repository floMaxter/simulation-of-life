package com.projects.simulation.entity;

public enum EntityType {
    GRASS("Grass"),
    ROCK("Rock"),
    TREE("Tree"),
    HERBIVORE("Herbivore"),
    PREDATOR("Predator"),
    GROUND("Ground");

    private final String name;

    EntityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
