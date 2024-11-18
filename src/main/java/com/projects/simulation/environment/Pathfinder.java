package com.projects.simulation.environment;

import com.projects.simulation.entity.EntityType;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class Pathfinder {

    private final WorldMap worldMap;

    public Pathfinder(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public Optional<PathToCell> findPath(Cell startCell, EntityType searchEntityType) {
        return BFS(startCell, searchEntityType);
    }

    private Optional<PathToCell> BFS(Cell startCell, EntityType searchEntityType) {
        Queue<Cell> queueCell = new ArrayDeque<>();
        PathToCell pathToCell = new PathToCell(startCell);

        pathToCell.putDistToCell(startCell, 0);
        pathToCell.putCameFromToCell(startCell, Cell.NULL_CELL);
        queueCell.offer(startCell);

        while (!queueCell.isEmpty()) {
            Cell currCell = queueCell.poll();
            EntityType currEntityType = worldMap.getEntityType(currCell);

            if (currEntityType.equals(searchEntityType)) {
                pathToCell.setTargetCell(currCell);
                return Optional.of(pathToCell);
            }

            for (Cell adj : worldMap.getAdjacentCell(currCell)) {
                if (!pathToCell.isVisitedCell(adj) && isGroundOrTargetCell(adj, searchEntityType)) {
                    pathToCell.putDistToCell(adj, pathToCell.getDistToCell(currCell) + 1);
                    pathToCell.putCameFromToCell(adj, currCell);
                    queueCell.offer(adj);
                }
            }
        }

        return Optional.empty();
    }

    protected boolean isGroundOrTargetCell(Cell cell, EntityType searchEntityType) {
        return worldMap.getEntityType(cell).equals(EntityType.GROUND)
                || worldMap.getEntityType(cell).equals(searchEntityType);
    }
}
