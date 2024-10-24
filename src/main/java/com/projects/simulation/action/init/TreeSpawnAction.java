package com.projects.simulation.action.init;

import com.projects.simulation.entity.Entity;
import com.projects.simulation.entity.inanimate.Tree;

public class TreeSpawnAction extends SpawnAction {
    @Override
    protected Entity spawnEntity() {
        return new Tree();
    }
}
