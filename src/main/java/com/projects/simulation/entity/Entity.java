package com.projects.simulation.entity;

import com.projects.simulation.environment.Coordinates;

public abstract class Entity {

    protected Coordinates coordinates;
    protected EntityType entityType;

    public EntityType getEntityType() {
        return entityType;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
