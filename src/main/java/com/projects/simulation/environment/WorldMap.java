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

    public void makeMove(Cell from, Cell to) {
        Entity entity = entities.get(from);
        entity.setCell(to);
        entities.remove(from);
        entities.put(to, entity);
    }

    //TODO: check that method return Optional.empty when coordinates is empty
    public Optional<Entity> getEntity(Cell cell) {
        return Optional.ofNullable(entities.get(cell));
    }

    public EntityType getEntityType(Cell cell) {
        Entity entity = entities.get(cell);
        return entity == null ? EntityType.GROUND : entity.getEntityType();
    }

    public Map<Cell, Creature> getCreatureCells() {
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

    public boolean isEmptyCell(Cell cell) {
        return !entities.containsKey(cell);
    }

    public boolean isValidCell(Cell cell) {
        return cell.getX() > 0 && cell.getX() <= width
                && cell.getY() > 0 && cell.getY() <= height;
    }

    public boolean isThereEntity(EntityType entityType) {
        return entities.entrySet().stream()
                .anyMatch(entity -> entity.getValue()
                        .getEntityType()
                        .equals(entityType));
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
