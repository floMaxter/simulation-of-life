package com.projects.simulation.action.init;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.inanimate.Rock;

public class RockSpawnAction extends SpawnAction {
    @Override
    protected Entity spawnEntity() {
        return new Rock();
    }
}
