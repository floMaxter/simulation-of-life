package com.projects.simulation.action.init;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.animate.Herbivore;

public class HerbivoreSpawnAction extends SpawnAction {
    @Override
    protected Entity spawnEntity() {
        return new Herbivore();
    }
}
