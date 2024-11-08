package com.projects.simulation.environment;

import com.projects.simulation.GameUtils;
import com.projects.simulation.action.Action;
import com.projects.simulation.action.init.GrassSpawnAction;
import com.projects.simulation.action.init.HerbivoreSpawnAction;
import com.projects.simulation.action.init.PredatorSpawnAction;
import com.projects.simulation.action.init.RockSpawnAction;
import com.projects.simulation.action.init.TreeSpawnAction;
import com.projects.simulation.action.turn.MoveAction;
import com.projects.simulation.entity.animate.Herbivore;
import com.projects.simulation.entity.inanimate.Grass;
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
    }

    private void generateInitActions() {
        this.initActions = new ArrayList<>();
        initActions.add(new GrassSpawnAction());
//        initActions.add(new RockSpawnAction());
//        initActions.add(new TreeSpawnAction());
//        initActions.add(new PredatorSpawnAction());
        initActions.add(new HerbivoreSpawnAction());
    }

    public void startGame() {
        consoleManager.printWelcomeWords();
        createNewMap();
    }

    private void createNewMap() {
        initWorld();
        worldMapRender.renderMap(worldMap);
        consoleManager.printGameFeatures();
        processUserInput();
    }

    private void initWorld() {
        this.worldMap = new WorldMap(GameUtils.WORLD_MAP_HEIGHT, GameUtils.WORLD_MAP_WIDTH);
        for (Action action : initActions) {
//            action.perform(worldMap);

            worldMap.setEntity(new Cell(1, 1), new Herbivore());
            worldMap.setEntity(new Cell(10, 10), new Grass());
        }
    }

    private void processUserInput() {
        int userChoice = consoleManager.readUserInput();
        switch (userChoice) {
            case GameUtils.OPTION_ONE_MOVE -> nextTurn();
            case GameUtils.OPTION_ENDLESS_CYCLE -> startSimulation();
            case GameUtils.OPTION_PAUSE_SIMULATION -> pauseSimulation();
            case GameUtils.OPTION_NEW_MAP -> createNewMap();
            case GameUtils.OPTION_EXIT -> endGame();
        }
    }

    private void nextTurn() {
        MoveAction moveAction = new MoveAction();
        moveAction.perform(worldMap);
        worldMapRender.renderMap(worldMap);
        consoleManager.printGameFeatures();
        processUserInput();
    }

    private void startSimulation() {

    }

    private void pauseSimulation() {

    }

    private void endGame() {
        consoleManager.printGoodByeWords();
    }
}
