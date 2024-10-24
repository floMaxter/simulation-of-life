package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.Entity;

public abstract class Creature extends Entity {
    protected int speed;
    protected int healthPoint;


    public abstract void makeMove();
}
