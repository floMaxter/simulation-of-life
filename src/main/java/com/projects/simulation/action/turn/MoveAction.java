package com.projects.simulation.action.turn;

import com.projects.simulation.action.Action;
import com.projects.simulation.entity.animate.Creature;
import com.projects.simulation.environment.Cell;
import com.projects.simulation.environment.WorldMap;

import java.util.Map;

public class MoveAction extends Action {
    @Override
    public void perform(WorldMap worldMap) {
        for (Map.Entry<Cell, Creature> entry : worldMap.getCellsWithCreature().entrySet()) {
            Creature creature = entry.getValue();
            creature.makeMove(worldMap);
        }
    }
}
