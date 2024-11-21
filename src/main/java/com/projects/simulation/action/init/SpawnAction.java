package com.projects.simulation.action.init;

import com.projects.simulation.action.Action;
import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.WorldMap;
import com.projects.simulation.utils.GameUtils;

import static com.projects.simulation.utils.GameUtils.getRandomIntInRange;

public abstract class SpawnAction extends Action {

    @Override
    public void perform(WorldMap worldMap) {
        int countTypeOnMap = getRandomNumberOfEntities(worldMap);
        for (int i = 0; i < countTypeOnMap; i++) {
            Cell randomCell = getRandomCell(worldMap);
            worldMap.setEntity(randomCell, spawnEntity());
        }
    }

    protected abstract Entity spawnEntity();

    private int getRandomNumberOfEntities(WorldMap map) {
        int minNumberOfTypeEntity = 1;
        int limitTypeForMap = map.getSize() / EntityType.values().length;
        return getRandomIntInRange(minNumberOfTypeEntity, limitTypeForMap);
    }

    private Cell getRandomCell(WorldMap map) {
        while (true) {
            int randX = getRandomIntInRange(GameUtils.MIN_COORDINATE_HEIGHT, GameUtils.MAX_COORDINATE_HEIGHT);
            int randY = getRandomIntInRange(GameUtils.MIN_COORDINATE_HEIGHT, GameUtils.MAX_COORDINATE_HEIGHT);
            Cell cell = new Cell(randX, randY);
            if (map.isEmptyCell(cell)) {
                return cell;
            }
        }
    }
}
