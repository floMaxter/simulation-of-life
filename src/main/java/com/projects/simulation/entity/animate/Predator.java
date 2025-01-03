package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.WorldMap;
import com.projects.simulation.path.PathToCell;
import com.projects.simulation.utils.GameUtils;

import java.util.Optional;

public class Predator extends Creature {

    private final int attackPower;

    public Predator() {
        this.entityType = EntityType.PREDATOR;
        this.preyType = EntityType.HERBIVORE;
        this.attackPower = GameUtils.ATTACK_POWER_PREDATOR;
        this.speed = GameUtils.SPEED_PREDATOR;
    }

    @Override
    protected void handlePreyInteraction(PathToCell pathToCell, WorldMap worldMap) {
        Cell preyCell = pathToCell.getTargetCell();
        if (worldMap.isAdjacentCells(this.cell, preyCell)) {
            handlePreyAction(preyCell, worldMap);
        } else {
            switchCell(pathToCell, worldMap);
        }
    }

    private void handlePreyAction(Cell preyCell, WorldMap worldMap) {
        Optional<Creature> optPrey = worldMap.getEntityAs(preyCell, Creature.class);
        if (optPrey.isPresent()) {
            Creature prey = optPrey.get();
            if (canEatPrey(prey)) {
                eatPrey(preyCell, worldMap);
            } else {
                attackPrey(prey, worldMap);
            }
        }
    }

    private boolean canEatPrey(Creature prey) {
        return prey.getHealthPoint() <= this.attackPower;
    }

    private void attackPrey(Creature prey, WorldMap worldMap) {
        int newHP = prey.getHealthPoint() - this.attackPower;
        prey.setHealthPoint(newHP);
        worldMap.setEntity(prey.getCell(), prey);
    }
}
