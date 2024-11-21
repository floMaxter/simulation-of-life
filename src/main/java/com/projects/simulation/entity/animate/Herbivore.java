package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.WorldMap;
import com.projects.simulation.path.PathToCell;
import com.projects.simulation.utils.GameUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Herbivore extends Creature {

    public Herbivore() {
        this.entityType = EntityType.HERBIVORE;
        this.preyType = EntityType.GRASS;
        this.healthPoint = GameUtils.HEALTH_POINT_HERBIVORE;
        this.speed = GameUtils.SPEED_HERBIVORE;
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        PathToCell pathToCell = findPrey(worldMap);
        if (pathToCell.isFoundTarget()) {
            if (canEatPrey(pathToCell.getTargetCell())) {
                eatPrey(pathToCell.getTargetCell(), worldMap);
            } else {
                switchCell(pathToCell, worldMap);
            }
        } else if (canMakeStep(worldMap)) {
            handleRandomMove(pathToCell, worldMap);
        } else {
            System.out.println("Herbivore " + this.getCell() +
                    " can't make a move because it is barricaded. The move is skipped.");
        }
    }

    private void handleRandomMove(PathToCell pathToCell, WorldMap worldMap) {
        List<Cell> potentialTargetCells = pathToCell.getDistToCells()
                .entrySet().stream()
                .filter(entry -> entry.getValue() <= this.speed + 1)
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Cell randTargetCell = potentialTargetCells
                .get(GameUtils.getRandomIntInRange(0, potentialTargetCells.size() - 1));
        pathToCell.setTargetCell(randTargetCell);
        switchCell(pathToCell, worldMap);
    }

    private void eatPrey(Cell cell, WorldMap worldMap) {
        worldMap.deleteEntity(cell);
    }

    private void switchCell(PathToCell pathToCell, WorldMap worldMap) {
        Optional<List<Cell>> optPath = pathToCell.getPath();
        if (optPath.isPresent()) {
            List<Cell> path = optPath.get();
            int countSteps = Math.min(speed, path.size());
            for (int i = 0; i < countSteps; i++) {
                worldMap.makeMove(cell, path.get(i));
            }
        } else {
            System.out.println("Path from " + pathToCell.getStartCell() + " is empty.");
        }
    }
}
