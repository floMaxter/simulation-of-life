package com.projects.simulation.environment;

import com.projects.simulation.entity.Entity;

import java.util.HashMap;
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
        entities.put(coordinates, entity);
    }

    public Entity getEntity(Coordinates coordinates) {
        // TODO: check valid coordinates and not empty
        return entities.get(coordinates);
    }

    public void deleteEntity(Coordinates coordinates) {
        entities.remove(coordinates);
    }

    public boolean isEmptyCoordinates(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
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
