package com.projects.simulation.entity.inanimate;

import com.projects.simulation.utils.GameUtils;
import com.projects.simulation.entity.EntityType;

public class Grass extends Nature {

    public Grass() {
        this.countNutrients = GameUtils.COUNT_NUTRIENTS_GRASS;
        this.entityType = EntityType.GRASS;
    }

}
