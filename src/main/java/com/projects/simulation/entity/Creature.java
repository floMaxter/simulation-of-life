package com.projects.simulation.entity;

public abstract class Creature extends Entity {
    protected int speed;
    protected int healthPoint;

    public abstract void makeMove();
}
