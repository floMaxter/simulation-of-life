package com.projects.simulation.render;

import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.WorldMap;

public class WorldMapRender {

    public static final String PREDATOR_EMOJI = "\uD83D\uDC3A";
    public static final String HERBIVORE_EMOJI = "\uD83D\uDC37";
    public static final String TREE_EMOJI = "\uD83C\uDF33";
    public static final String ROCK_EMOJI = "\uD83C\uDFD4";
    public static final String GRASS_EMOJI = "\uD83C\uDF3D";
    public static final String GROUND_EMOJI = "\u2B1B";

    public void renderMap(WorldMap worldMap) {
        //System.out.println();
        System.out.println("================================");
        for (int i = worldMap.getHeight(); i > 0; i--) {
            for (int j = 1; j <= worldMap.getWidth() ; j++) {
                Cell cell = new Cell(j, i);
                switch (worldMap.getEntityType(cell)) {
                    case GRASS -> System.out.print(GRASS_EMOJI + " ");
                    case TREE -> System.out.print(TREE_EMOJI + " ");
                    case ROCK -> System.out.print(ROCK_EMOJI + " ");
                    case PREDATOR -> System.out.print(PREDATOR_EMOJI + " ");
                    case HERBIVORE -> System.out.print(HERBIVORE_EMOJI + " ");
                    case GROUND -> System.out.print(GROUND_EMOJI + " ");
                }
            }
            System.out.println();
        }
        System.out.println("================================");
    }
}
