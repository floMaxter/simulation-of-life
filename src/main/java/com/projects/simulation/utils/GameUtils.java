package com.projects.simulation.utils;

import java.util.Random;

public class GameUtils {

    public static final int WORLD_MAP_HEIGHT = 10;
    public static final int WORLD_MAP_WIDTH = 10;
    public static final int MIN_COORDINATE_HEIGHT = 1;
    public static final int MAX_COORDINATE_HEIGHT = 10;

    public static final int HEALTH_POINT_HERBIVORE = 1;
    public static final int ATTACK_POWER_PREDATOR = 1;
    public static final int COUNT_NUTRIENTS_TREE = 3;
    public static final int COUNT_NUTRIENTS_GRASS = 1;

    public static final int SPEED_HERBIVORE = 2;
    public static final int SPEED_PREDATOR = 3;

    public static int getRandomIntInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
