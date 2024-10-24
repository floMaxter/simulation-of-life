package com.projects.simulation.render;

import com.projects.simulation.environment.Coordinates;
import com.projects.simulation.environment.WorldMap;

public class WorldMapRender {

    public static final String PREDATOR_EMOJI = "\uD83E\uDD81";
    public static final String HERBIVORE_EMOJI = "\uD83D\uDC37";
    public static final String TREE_EMOJI = "\uD83C\uDF33";
    public static final String ROCK_EMOJI = "\uD83E\uDEA8";
    public static final String GRASS_EMOJI = "\uD83C\uDF3D";
    public static final String EMPTY_CELL_EMOJI = "\uD83D\uDFEB";

    public void renderMap(WorldMap worldMap) {
        System.out.println();
        for (int i = worldMap.getHeight(); i > 0; i--) {
            for (int j = worldMap.getWidth(); j > 0 ; j--) {
                Coordinates coordinates = new Coordinates(j, i);
                if (worldMap.isEmptyCoordinates(coordinates)) {
                    System.out.print(" " + EMPTY_CELL_EMOJI + " ");
                } else {
                    switch (worldMap.getEntity(coordinates).getEntityType()) {
                        case GRASS -> System.out.print(" " + GRASS_EMOJI + " ");
                        case TREE -> System.out.print(" " + TREE_EMOJI + " ");
                        case ROCK -> System.out.print(" " + ROCK_EMOJI + " ");
                        case PREDATOR -> System.out.print(" " + PREDATOR_EMOJI + " ");
                        case HERBIVORE -> System.out.print(" " + HERBIVORE_EMOJI + " ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
