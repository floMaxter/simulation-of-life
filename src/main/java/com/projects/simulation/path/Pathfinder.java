package com.projects.simulation.path;

import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.WorldMap;

import java.util.ArrayDeque;
import java.util.Queue;

public class Pathfinder implements PathfindingStrategy {

    private final WorldMap worldMap;

    public Pathfinder(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public PathToCell findPath(Cell startCell, EntityType searchEntityType) {
        return BFS(startCell, searchEntityType, worldMap);
    }

    private PathToCell BFS(Cell startCell, EntityType searchEntityType, WorldMap worldMap) {
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
                break;
            }

            for (Cell adj : worldMap.getAdjacentCell(currCell)) {
                if (pathToCell.isUnvisitedCell(adj) && isGroundOrTargetCell(adj, searchEntityType)) {
                    pathToCell.putDistToCell(adj, pathToCell.getDistToCell(currCell) + 1);
                    pathToCell.putCameFromToCell(adj, currCell);
                    queueCell.offer(adj);
                }
            }
        }

        return pathToCell;
    }


    private boolean isGroundOrTargetCell(Cell cell, EntityType searchEntityType) {
        return isGroundCell(cell) || isSearchingCell(cell, searchEntityType);
    }

    private boolean isGroundCell(Cell cell) {
        return worldMap.getEntityType(cell).equals(EntityType.GROUND);
    }

    private boolean isSearchingCell(Cell cell, EntityType searchEntityType) {
        return worldMap.getEntityType(cell).equals(searchEntityType);
    }
}
