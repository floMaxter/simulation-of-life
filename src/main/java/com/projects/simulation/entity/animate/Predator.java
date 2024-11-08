package com.projects.simulation.entity.animate;

import com.projects.simulation.GameUtils;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.WorldMap;

public class Predator extends Creature {

    private final int attackPower;

    public Predator() {
        this.entityType = EntityType.PREDATOR;
        this.preyType = EntityType.HERBIVORE;
        this.attackPower = GameUtils.ATTACK_POWER_PREDATOR;
        this.speed = GameUtils.SPEED_PREDATOR;
    }

    @Override
    public void makeMove(WorldMap worldMap) {
    }
}
