package com.projects.simulation.entity.animate;

import com.projects.simulation.utils.GameUtils;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.PathToCell;
import com.projects.simulation.environment.WorldMap;

import java.util.List;
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
        if (isTherePrey(worldMap)) {
            handleExistingPrey(worldMap);
        } else if (canMakeRandomMove(worldMap)) {
            handleRandomMove(worldMap);
        } else {
            System.out.println("Herbivore " + this.getCell() +
                    " can't make a move because it is barricaded. The move is skipped.");
        }
    }

    private void handleExistingPrey(WorldMap worldMap) {
        Optional<PathToCell> optPathToPrey = findPrey(worldMap);
        if (optPathToPrey.isPresent()) {
            PathToCell pathToCell = optPathToPrey.get();
            if (canEatPrey(pathToCell.getTargetCell())) {
                eatPrey(pathToCell.getTargetCell(), worldMap);
            } else {
                switchCell(pathToCell, worldMap);
            }
        } else if (canMakeRandomMove(worldMap)) {
            handleRandomMove(worldMap);
        } else {
            System.out.println("Herbivore " + this.getCell() +
                    " can't make a move because it is barricaded. The move is skipped.");
        }
    }

    private void handleRandomMove(WorldMap worldMap) {
        PathToCell pathToCell = new PathToCell(this.cell);
        Cell currCell = this.cell;
        Cell nextCell;

        pathToCell.putDistToCell(currCell, 0);
        pathToCell.putCameFromToCell(currCell, Cell.NULL_CELL);

        for (int i = 0; i < this.speed; i++) {
            List<Cell> adjCell = worldMap.getAdjacentCell(currCell);
            Optional<Cell> optNextCell = selectEmptyRandomCell(adjCell, worldMap);

            if (optNextCell.isPresent() && !pathToCell.isVisitedCell(optNextCell.get())) {
                nextCell = optNextCell.get();
                pathToCell.putDistToCell(nextCell, pathToCell.getDistToCell(currCell));
                pathToCell.putCameFromToCell(nextCell, currCell);
                currCell = nextCell;
            } else {
                pathToCell.setTargetCell(currCell);
                switchCell(pathToCell, worldMap);
                break;
            }
        }
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

    private Optional<Cell> selectEmptyRandomCell(List<Cell> cells, WorldMap worldMap) {
        List<Cell> emptyCells = cells.stream()
                .filter(cell -> worldMap.getEntityType(cell).equals(EntityType.GROUND))
                .collect(Collectors.toList());
        return emptyCells.isEmpty() ? Optional.empty() : selectRandomCell(emptyCells);
    }

    private Optional<Cell> selectRandomCell(List<Cell> cells) {
        int randIndex = GameUtils.getRandomIntInRange(0, cells.size() - 1);
        return Optional.ofNullable(cells.get(randIndex));
    }
}
