package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.PathToCell;
import com.projects.simulation.environment.Pathfinder;
import com.projects.simulation.environment.WorldMap;

import java.util.Optional;

public abstract class Creature extends Entity {

    protected int speed;
    protected int healthPoint;
    protected EntityType preyType;

    public abstract void makeMove(WorldMap worldMap);

    protected Optional<PathToCell> findPrey(WorldMap worldMap) {
        Pathfinder pathfinder = new Pathfinder(worldMap);
        return pathfinder.findPath(cell, preyType);
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

    protected boolean isTherePrey(WorldMap worldMap) {
        return worldMap.isThereEntity(this.preyType);
    }
}
