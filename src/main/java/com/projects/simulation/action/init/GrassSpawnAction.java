package com.projects.simulation.action.init;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.inanimate.Grass;

public class GrassSpawnAction extends SpawnAction {

    @Override
    protected Entity spawnEntity() {
        return new Grass();
    }
}
