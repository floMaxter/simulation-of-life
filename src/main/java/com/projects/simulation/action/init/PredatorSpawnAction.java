package com.projects.simulation.action.init;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.animate.Predator;

public class PredatorSpawnAction extends SpawnAction {

    @Override
    protected Entity spawnEntity() {
        return new Predator();
    }
}
