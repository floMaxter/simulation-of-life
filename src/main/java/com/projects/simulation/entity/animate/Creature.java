package com.projects.simulation.entity.animate;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Coordinates;
import com.projects.simulation.environment.WorldMap;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public abstract class Creature extends Entity {

    protected int speed;
    protected int healthPoint;
    protected EntityType preyType;

    public abstract void makeMove(WorldMap worldMap);

    protected Optional<Coordinates> findPrey(WorldMap worldMap) {
        Queue<Coordinates> queueCoordinates = new ArrayDeque<>();
        Set<Coordinates> processedOrQueued = new HashSet<>();

        queueCoordinates.offer(this.coordinates);
        while(!queueCoordinates.isEmpty()) {
            Coordinates currCoordinates = queueCoordinates.poll();
            EntityType entityTypeOnCell = worldMap.getEntity(currCoordinates).getEntityType();
            if (entityTypeOnCell.equals(preyType)) {
                return Optional.of(currCoordinates);
            }
            for (Coordinates adj : worldMap.getAdjacentCoordinates(this.coordinates)) {
                if (!processedOrQueued.contains(adj)) {
                    queueCoordinates.offer(adj);
                    processedOrQueued.add(adj);
                }
            }
        }

        return Optional.empty();
    }
}
