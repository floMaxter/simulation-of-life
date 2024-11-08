package com.projects.simulation.environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathToPrey {

    private Cell preyCell;
    private final Map<Cell, Cell> cameFromMap;
    private final Map<Cell, Integer> distToPrey;
    private final List<Cell> path;

    public PathToPrey() {
        this.preyCell = new Cell(-1, -1);
        this.cameFromMap = new HashMap<>();
        this.distToPrey = new HashMap<>();
        this.path = new ArrayList<>();
    }

    public void putDistToCell(Cell cell, Integer dist) {
        this.distToPrey.put(cell, dist);
    }

    public void putCameFromToCell(Cell to, Cell from) {
        this.cameFromMap.put(to, from);
    }

    public Integer getDistToCell(Cell cell) {
        return this.distToPrey.get(cell);
    }

    public Cell getPreyCell() {
        return this.preyCell;
    }

    public List<Cell> getPath() {
        if (!path.isEmpty()) {
            return path;
        }

        Cell endCell = new Cell(-1, -1);
        for (Cell c = this.preyCell; !c.equals(endCell); c = this.cameFromMap.get(c)) {
            path.add(c);
        }
        Collections.reverse(this.path);
        return path;
    }

    public boolean isVisitedCell(Cell cell) {
        return distToPrey.containsKey(cell);
    }

    public void setPreyCell(Cell preyCell) {
        this.preyCell = preyCell;
    }
}
