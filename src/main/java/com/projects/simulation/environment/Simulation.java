package com.projects.simulation.environment;

import com.projects.simulation.GameUtils;
import com.projects.simulation.action.Action;
import com.projects.simulation.action.init.GrassSpawnAction;
import com.projects.simulation.action.init.HerbivoreSpawnAction;
import com.projects.simulation.action.init.PredatorSpawnAction;
import com.projects.simulation.action.init.RockSpawnAction;
import com.projects.simulation.action.init.TreeSpawnAction;
import com.projects.simulation.io.ConsoleManager;
import com.projects.simulation.render.WorldMapRender;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private WorldMap worldMap;
    private final Integer moveCount;

    private final ConsoleManager consoleManager;

    private final WorldMapRender worldMapRender;

    private List<Action> initActions;

    public Simulation() {
        this.moveCount = 0;
        this.consoleManager = new ConsoleManager();
        this.worldMapRender = new WorldMapRender();
        generateInitActions();
        initWorld();
    }

    private void generateInitActions() {
        this.initActions = new ArrayList<>();
        initActions.add(new GrassSpawnAction());
        initActions.add(new RockSpawnAction());
        initActions.add(new TreeSpawnAction());
        initActions.add(new PredatorSpawnAction());
        initActions.add(new HerbivoreSpawnAction());
    }

    private void initWorld() {
        this.worldMap = new WorldMap(GameUtils.WORLD_MAP_HEIGHT, GameUtils.WORLD_MAP_WIDTH);
        for (Action action : initActions) {
            action.perform(worldMap);
        }
    }

    public void startGame() {
        consoleManager.printWelcomeWords();
        worldMapRender.renderMap(worldMap);
        consoleManager.printGameFeatures();
        int userChoice = consoleManager.readUserInput();
        switch (userChoice) {
            case GameUtils.ONE_MOVE_OF_SIMULATION -> nextTurn();
            case GameUtils.ENDLESS_SIMULATION -> startSimulation();
            case GameUtils.CREATE_NEW_MAP -> createNewMap();
            case GameUtils.EXIT -> endGame();
        }
    }

    private void nextTurn() {

    }

    private void startSimulation() {

    }

    private void createNewMap() {

    }

    private void pauseSimulation() {

    }

    private void endGame() {
        consoleManager.printGoodByeWords();
    }
}
