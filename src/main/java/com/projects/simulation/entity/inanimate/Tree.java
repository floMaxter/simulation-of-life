package com.projects.simulation.entity.inanimate;

import com.projects.simulation.utils.GameUtils;
import com.projects.simulation.entity.EntityType;

public class Tree extends Nature {

    public Tree() {
        this.countNutrients = GameUtils.COUNT_NUTRIENTS_TREE;
        this.entityType = EntityType.TREE;
    }
}
