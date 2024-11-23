package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.WorldMap;
import com.projects.simulation.io.ConsoleManager;
import com.projects.simulation.path.PathToCell;
import com.projects.simulation.utils.GameUtils;

public class Herbivore extends Creature {

    public Herbivore() {
        this.entityType = EntityType.HERBIVORE;
        this.preyType = EntityType.GRASS;
        this.healthPoint = GameUtils.HEALTH_POINT_HERBIVORE;
        this.speed = GameUtils.SPEED_HERBIVORE;
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        PathToCell pathToCell = findPrey(preyType, worldMap);
        if (isFoundPrey(pathToCell)) {
            handlePreyInteraction(pathToCell, worldMap);
        } else if (canMakeStep(worldMap)) {
            makeRandomMove(pathToCell, worldMap);
        } else {
            ConsoleManager.printInfo("Herbivore " + this.cell +
                    "can't make a move because it is barricaded. The move is skipped.");
        }
    }

    private void handlePreyInteraction(PathToCell pathToCell, WorldMap worldMap) {
        Cell preyCell = pathToCell.getTargetCell();
        if (worldMap.isAdjacentCells(this.cell, preyCell)) {
            eatPrey(pathToCell.getTargetCell(), worldMap);
        } else {
            switchCell(pathToCell, worldMap);
        }
    }
}
