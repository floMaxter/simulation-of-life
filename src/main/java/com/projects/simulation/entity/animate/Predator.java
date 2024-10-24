package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.EntityType;

public class Predator extends Creature {

    private int attackPower;

    public Predator() {
        this.entityType = EntityType.PREDATOR;
    }

    @Override
    public void makeMove() {

    }
}
