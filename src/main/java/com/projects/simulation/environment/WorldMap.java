package com.projects.simulation.environment;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.entity.animate.Creature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WorldMap {

    private final Integer height;
    private final Integer width;

    private final Map<Cell, Entity> entities;

    public WorldMap(Integer height, Integer width) {
        this.width = width;
        this.height = height;
        entities = new HashMap<>();
    }

    public void setEntity(Cell cell, Entity entity) {
        entity.setCell(cell);
        entities.put(cell, entity);
    }

    public void makeMove(Cell distinationCell, Creature creature) {
        deleteEntity(creature.getCell());
        creature.setCell(distinationCell);
        entities.put(distinationCell, creature);
    }

    public Optional<Entity> getEntity(Cell cell) {
        return Optional.ofNullable(entities.get(cell));
    }

    public <T extends Entity> Optional<T> getEntityAs(Cell cell, Class<T> entityTypeClass) {
        Entity entity = entities.get(cell);
        if (entityTypeClass.isInstance(entity)) {
            return Optional.of(entityTypeClass.cast(entity));
        }
        return Optional.empty();
    }

    public EntityType getEntityType(Cell cell) {
        Entity entity = entities.get(cell);
        return entity == null ? EntityType.GROUND : entity.getEntityType();
    }

    public Map<Cell, Creature> getCellsWithCreature() {
        Map<Cell, Creature> creatureCells = new HashMap<>();
        for (Map.Entry<Cell, Entity> entry : this.entities.entrySet()) {
            if (entry.getValue() instanceof Creature) {
                creatureCells.put(entry.getKey(), (Creature) entry.getValue());
            }
        }
        return creatureCells;
    }

    public List<Cell> getAdjacentCell(Cell cell) {
        List<Cell> adjCell = new ArrayList<>();
        List<Cell> potentialAdjCell = getPotentialAdjacentCell(cell);
        for (Cell currCell : potentialAdjCell) {
            if (this.isValidCell(currCell)) {
                adjCell.add(currCell);
            }
        }
        return adjCell;
    }

    /**
     * Returns a list of potential adjacent coordinates around the current entity's coordinates,
     * order in a clockwise direction starting from the top.
     *
     * @param cell the central {@code Coordinates} around which to find adjacent points
     * @return a list of adjacent {@code Coordinates} in clockwise order
     * */
    private List<Cell> getPotentialAdjacentCell(Cell cell) {
        return Arrays.asList(
                new Cell(cell.getX(), cell.getY() + 1),
                new Cell(cell.getX() + 1, cell.getY() + 1),
                new Cell(cell.getX() + 1, cell.getY()),
                new Cell(cell.getX() + 1, cell.getY() - 1),
                new Cell(cell.getX(), cell.getY() - 1),
                new Cell(cell.getX() - 1, cell.getY() - 1),
                new Cell(cell.getX() - 1, cell.getY()),
                new Cell(cell.getX() - 1, cell.getY() + 1)
        );
    }

    public void deleteEntity(Cell cell) {
        entities.remove(cell);
    }

    public boolean isAdjacentCells(Cell f, Cell s) {
        int dx = Math.abs(f.getX() - s.getX());
        int dy = Math.abs(f.getY() - s.getY());
        return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
    }

    public boolean isEmptyCell(Cell cell) {
        return !entities.containsKey(cell);
    }

    public boolean isValidCell(Cell cell) {
        return cell.getX() > 0 && cell.getX() <= width
                && cell.getY() > 0 && cell.getY() <= height;
    }

    public boolean isEntityTypePresent(EntityType entityType) {
        return entities.values().stream()
                .anyMatch(entity -> entity.getEntityType().equals(entityType));
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

    public Integer getNumberOfEntity(EntityType entityType) {
        return Math.toIntExact(entities.values().stream()
                .filter(entity -> entity.getEntityType().equals(entityType))
                .count());
    }
}
