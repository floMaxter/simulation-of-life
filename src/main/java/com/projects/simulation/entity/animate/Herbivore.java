package com.projects.simulation.entity.animate;

import com.projects.simulation.GameUtils;
import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Coordinates;
import com.projects.simulation.environment.WorldMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class Herbivore extends Creature {

    public Herbivore() {
        this.entityType = EntityType.HERBIVORE;
        this.preyType = EntityType.GRASS;
        this.healthPoint = GameUtils.HEALTH_POINT_HERBIVORE;
        this.speed = GameUtils.SPEED_HERBIVORE;
    }

    @Override
    public void makeMove(WorldMap worldMap) {

    }

    private void eatPrey(Coordinates coordinates, WorldMap worldMap) {
        worldMap.deleteEntity(coordinates);
    }

    //TODO: add shiftCoordinates
    private void switchCoordinates(WorldMap worldMap) {

    }
}
