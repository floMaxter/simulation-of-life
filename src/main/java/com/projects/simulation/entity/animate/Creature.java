package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.WorldMap;
import com.projects.simulation.io.ConsoleWriter;
import com.projects.simulation.path.PathToCell;
import com.projects.simulation.path.Pathfinder;
import com.projects.simulation.path.PathfindingStrategy;
import com.projects.simulation.utils.GameUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Creature extends Entity {

    protected int speed;
    protected int healthPoint;
    protected EntityType preyType;

    public void makeMove(WorldMap worldMap) {
        PathToCell pathToCell = findPrey(preyType, worldMap);
        if (isFoundPrey(pathToCell)) {
            handlePreyInteraction(pathToCell, worldMap);
        } else if (canMakeStep(worldMap)) {
            makeRandomMove(pathToCell, worldMap);
        } else {
            ConsoleWriter.printMessage(this.entityType.getName() + " " + this.cell +
                    " can't make a move because it is barricaded. The move is skipped.");
        }
    }

    protected abstract void handlePreyInteraction(PathToCell pathToCell, WorldMap worldMap);

    protected void makeRandomMove(PathToCell pathToCell, WorldMap worldMap) {
        int maxSearchRadius = speed + 1;
        List<Cell> potentialTargetCells = pathToCell.getDistToCells()
                .entrySet().stream()
                .filter(entry -> entry.getValue() <= maxSearchRadius)
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Cell randTargetCell = potentialTargetCells
                .get(GameUtils.getRandomIntInRange(0, potentialTargetCells.size() - 1));
        pathToCell.setTargetCell(randTargetCell);
        switchCell(pathToCell, worldMap);
    }

    protected PathToCell findPrey(EntityType preyType, WorldMap worldMap) {
        PathfindingStrategy pathfinder = new Pathfinder(worldMap);
        return pathfinder.findPath(cell, preyType);
    }

    protected void eatPrey(Cell cell, WorldMap worldMap) {
        worldMap.deleteEntity(cell);
    }

    protected void switchCell(PathToCell pathToCell, WorldMap worldMap) {
        Optional<List<Cell>> optPath = pathToCell.getPath();
        if (optPath.isPresent()) {
            List<Cell> path = optPath.get();
            int countSteps = Math.min(speed, path.size());
            for (int i = 0; i < countSteps; i++) {
                worldMap.makeMove(path.get(i), this);
            }
        } else {
            ConsoleWriter.printMessage("Path from " + pathToCell.getStartCell() + " is empty.");
        }
    }

    protected boolean canMakeStep(WorldMap worldMap) {
        return worldMap.getAdjacentCell(this.cell).stream()
                .anyMatch(entity -> worldMap.getEntityType(entity).equals(EntityType.GROUND));
    }

    protected boolean isFoundPrey(PathToCell pathToCell) {
        return pathToCell.isFoundTarget();
    }

    public int getHealthPoint() {
        return this.healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }
}
