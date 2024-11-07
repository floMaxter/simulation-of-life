package com.projects.simulation.environment;

import com.projects.simulation.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMap {

    private final Integer height;
    private final Integer width;

    private final Map<Coordinates, Entity> entities;

    public WorldMap(Integer height, Integer width) {
        this.width = width;
        this.height = height;
        entities = new HashMap<>();
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        entities.put(coordinates, entity);
    }

    public Entity getEntity(Coordinates coordinates) {
        // TODO: check valid coordinates and not empty
        return entities.get(coordinates);
    }

    public List<Coordinates> getAdjacentCoordinates(Coordinates coordinates) {
        List<Coordinates> adjacentCoordinates = new ArrayList<>();
        for (Coordinates currCoordinates : getPotentialAdjacentCoordinates(coordinates)) {
            if (this.isValidCoordinates(currCoordinates)) {
                adjacentCoordinates.add(currCoordinates);
            }
        }
        return adjacentCoordinates;
    }

    /**
     * Returns a list of potential adjacent coordinates around the current entity's coordinates,
     * order in a clockwise direction starting from the top.
     *
     * @param coordinates the central {@code Coordinates} around which to find adjacent points
     * @return a list of adjacent {@code Coordinates} in clockwise order
     * */
    private List<Coordinates> getPotentialAdjacentCoordinates(Coordinates coordinates) {
        return Arrays.asList(
                new Coordinates(coordinates.getX(), coordinates.getY() + 1),
                new Coordinates(coordinates.getX() + 1, coordinates.getY() + 1),
                new Coordinates(coordinates.getX() + 1, coordinates.getY()),
                new Coordinates(coordinates.getX() + 1, coordinates.getY() - 1),
                new Coordinates(coordinates.getX(), coordinates.getY() - 1),
                new Coordinates(coordinates.getX() - 1, coordinates.getY())
        );
    }

    public void deleteEntity(Coordinates coordinates) {
        entities.remove(coordinates);
    }

    public boolean isEmptyCoordinates(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
    }

    public boolean isValidCoordinates(Coordinates coordinates) {
        return coordinates.getX() > 0 && coordinates.getX() <= width
                && coordinates.getY() > 0 && coordinates.getY() <= height;
    }

    public Integer getSize() {
        return height * width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }
}
