package com.projects.simulation.path;

import com.projects.simulation.environment.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PathToCell {

    private final Cell startCell;
    private Cell targetCell;
    private final Map<Cell, Cell> cameFromMap;
    private final Map<Cell, Integer> distToCells;
    private final List<Cell> path;

    public PathToCell(Cell startCell) {
        this.startCell = startCell;
        this.targetCell = Cell.NULL_CELL;
        this.cameFromMap = new HashMap<>();
        this.distToCells = new HashMap<>();
        this.path = new ArrayList<>();
    }

    public void putDistToCell(Cell cell, Integer dist) {
        this.distToCells.put(cell, dist);
    }

    public void putCameFromToCell(Cell to, Cell from) {
        this.cameFromMap.put(to, from);
    }

    public void setTargetCell(Cell targetCell) {
        this.targetCell = targetCell;
    }

    public Integer getDistToCell(Cell cell) {
        return this.distToCells.get(cell);
    }

    public Map<Cell, Integer> getDistToCells() {
        return this.distToCells;
    }

    public Cell getTargetCell() {
        return this.targetCell;
    }

    public Cell getStartCell() {
        return this.startCell;
    }


    /**
     * Retrieves the path from the start cell to the target cell, if it exists.
     * <p>
     * If the path has already been computed, it is returned from the cached result.
     * If the target cell is not set (equal to {@code Cell.NULL_CELL}), an empty {@code Optional} is returned.
     * Otherwise, the path is reconstructed by backtracking through the {@code cameFromMap}
     * and returned as an {@code Optional} containing the list of cells.
     * </p>
     *
     * @return an {@code Optional} containing the list of {@code Cell} objects representing the path
     *         from the start cell to the target cell, or an empty {@code Optional} if no path exists.
     */
    public Optional<List<Cell>> getPath() {
        if (!path.isEmpty()) {
            return Optional.of(path);
        }
        if (targetCell == Cell.NULL_CELL || targetCell == startCell) {
            return Optional.empty();
        }

        for (Cell c = cameFromMap.get(targetCell); !c.equals(startCell); c = cameFromMap.get(c)) {
            path.add(c);
        }
        Collections.reverse(path);
        return Optional.of(path);
    }

    public boolean isFoundTarget() {
        return targetCell != Cell.NULL_CELL;
    }

    public boolean isUnvisitedCell(Cell cell) {
        return !distToCells.containsKey(cell);
    }
}
