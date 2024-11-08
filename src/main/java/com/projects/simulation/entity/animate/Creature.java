package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.PathToPrey;
import com.projects.simulation.environment.WorldMap;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public abstract class Creature extends Entity {

    protected int speed;
    protected int healthPoint;
    protected EntityType preyType;

    public abstract void makeMove(WorldMap worldMap);

    protected Optional<PathToPrey> findPrey(WorldMap worldMap) {
        Queue<Cell> queueCell = new ArrayDeque<>();
        PathToPrey pathToPrey = new PathToPrey();

        pathToPrey.putDistToCell(this.cell, 0);
        pathToPrey.putCameFromToCell(this.cell, new Cell(-1, -1));
        queueCell.offer(this.cell);

        while (!queueCell.isEmpty()) {
            Cell currCell = queueCell.poll();
            EntityType currEntityType = worldMap.getEntityType(currCell);

            if (currEntityType.equals(this.preyType)) {
                pathToPrey.setPreyCell(currCell);
                return Optional.of(pathToPrey);
            }

            for (Cell adj : worldMap.getAdjacentCell(currCell)) {
                if (!pathToPrey.isVisitedCell(adj)) {
                    pathToPrey.putDistToCell(adj, pathToPrey.getDistToCell(currCell) + 1);
                    pathToPrey.putCameFromToCell(adj, currCell);
                    queueCell.offer(adj);
                }
            }

            /*if (currEntityType.equals(EntityType.GROUND)) {
                for (Cell adj : worldMap.getAdjacentCell(currCell)) {
                    if (!pathToPrey.isVisitedCell(adj)) {
                        pathToPrey.putDistToCell(adj, pathToPrey.getDistToCell(currCell) + 1);
                        pathToPrey.putCameFromToCell(adj, currCell);
                        queueCell.offer(adj);
                    }
                }
            }*/
        }

        return Optional.empty();
    }

    protected boolean canEatPrey(Cell locationPrey) {
        return Math.abs(this.cell.getX() - locationPrey.getX()) == 1
                && Math.abs(this.cell.getY() - locationPrey.getY()) == 1;
    }
}
