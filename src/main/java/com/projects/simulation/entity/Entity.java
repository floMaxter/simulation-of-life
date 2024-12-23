package com.projects.simulation.entity;

import com.projects.simulation.environment.Cell;

public abstract class Entity {

    protected Cell cell;
    protected EntityType entityType;

    public EntityType getEntityType() {
        return entityType;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
