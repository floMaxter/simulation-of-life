package com.projects.simulation.path;

import com.projects.simulation.entity.EntityType;
import com.projects.simulation.environment.Cell;

public interface PathfindingStrategy {

    PathToCell findPath(Cell startCell, EntityType searchingEntityType);
}
