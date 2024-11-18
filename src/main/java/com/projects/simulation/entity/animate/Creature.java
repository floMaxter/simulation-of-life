package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.PathToCell;
import com.projects.simulation.environment.WorldMap;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public abstract class Creature extends Entity {

    protected int speed;
    protected int healthPoint;
    protected EntityType preyType;

    public abstract void makeMove(WorldMap worldMap);

    protected Optional<PathToCell> findPrey(WorldMap worldMap) {
        Queue<Cell> queueCell = new ArrayDeque<>();
        PathToCell pathToCell = new PathToCell(this.cell);

        pathToCell.putDistToCell(this.cell, 0);
        pathToCell.putCameFromToCell(this.cell, Cell.NULL_CELL);
        queueCell.offer(this.cell);

        while (!queueCell.isEmpty()) {
            Cell currCell = queueCell.poll();
            EntityType currEntityType = worldMap.getEntityType(currCell);

            if (currEntityType.equals(this.preyType)) {
                pathToCell.setTargetCell(currCell);
                return Optional.of(pathToCell);
            }

            if (currEntityType.equals(EntityType.GROUND) || isStartCell(currCell)) {
                for (Cell adj : worldMap.getAdjacentCell(currCell)) {
                    if (!pathToCell.isVisitedCell(adj)) {
                        pathToCell.putDistToCell(adj, pathToCell.getDistToCell(currCell) + 1);
                        pathToCell.putCameFromToCell(adj, currCell);
                        queueCell.offer(adj);
                    }
                }
            }
        }

        return Optional.empty();
    }

    protected boolean canEatPrey(Cell preyCell) {
        int dx = Math.abs(cell.getX() - preyCell.getX());
        int dy = Math.abs(cell.getY() - preyCell.getY());
        return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
    }

    protected boolean canMakeRandomMove(WorldMap worldMap) {
        return worldMap.getAdjacentCell(this.cell).stream()
                .anyMatch(entity -> worldMap.getEntityType(entity).equals(EntityType.GROUND));
    }

    protected boolean isStartCell(Cell cell) {
        return cell.equals(this.cell);
    }

    protected boolean isTherePrey(WorldMap worldMap) {
        return worldMap.isThereEntity(this.preyType);
    }
}
