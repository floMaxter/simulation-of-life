package com.projects.simulation.entity.animate;

import com.projects.simulation.GameUtils;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.PathToPrey;
import com.projects.simulation.environment.WorldMap;

import java.util.List;
import java.util.Optional;

public class Herbivore extends Creature {

    public Herbivore() {
        this.entityType = EntityType.HERBIVORE;
        this.preyType = EntityType.GRASS;
        this.healthPoint = GameUtils.HEALTH_POINT_HERBIVORE;
        this.speed = GameUtils.SPEED_HERBIVORE;
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        Optional<PathToPrey> pathToPreyOptional = findPrey(worldMap);
        if (pathToPreyOptional.isPresent()) {
            PathToPrey pathToPrey = pathToPreyOptional.get();
            if (canEatPrey(pathToPrey.getPreyCell())) {
                eatPrey(pathToPrey.getPreyCell(), worldMap);
            } else {
                switchCell(pathToPrey, worldMap);
            }
        } else {
            System.out.println("Map doesn't contain gross for herbivore");
        }
    }

    private void eatPrey(Cell cell, WorldMap worldMap) {
        worldMap.deleteEntity(cell);
    }

    private void switchCell(PathToPrey pathToPrey, WorldMap worldMap) {
        List<Cell> path = pathToPrey.getPath();
        for (int i = 0; i < this.speed; i++) {
            worldMap.makeMove(this.cell, path.get(i));
        }
    }
}
