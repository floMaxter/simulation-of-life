package com.projects.simulation.action.init;

import com.projects.simulation.utils.GameUtils;
import com.projects.simulation.action.Action;
import com.projects.simulation.entity.Entity;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.WorldMap;

import java.util.Random;

public abstract class SpawnAction extends Action {

    @Override
    public void perform(WorldMap worldMap) {
        int countTypeOnMap = getRandomNumberOfEntities(worldMap);
        for (int i = 0; i < countTypeOnMap; i++) {
            Cell randomCell = getRandomCoordinates(worldMap);
            worldMap.setEntity(randomCell, spawnEntity());
        }
    }

    protected abstract Entity spawnEntity();

    private int getRandomNumberOfEntities(WorldMap map) {
        int minNumberOfTypeEntity = 1;
        int limitTypeForMap = map.getSize() / EntityType.values().length;
        return getRandomIntInRange(minNumberOfTypeEntity, limitTypeForMap);
    }

    private Cell getRandomCoordinates(WorldMap map) {
        while (true) {
            int randHeight = getRandomIntInRange(GameUtils.MIN_COORDINATE_HEIGHT, GameUtils.MAX_COORDINATE_HEIGHT);
            int randWidth = getRandomIntInRange(GameUtils.MIN_COORDINATE_HEIGHT, GameUtils.MAX_COORDINATE_HEIGHT);
            Cell cell = new Cell(randHeight, randWidth);
            if (map.isEmptyCell(cell)) {
                return cell;
            }
        }
    }

    private int getRandomIntInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
