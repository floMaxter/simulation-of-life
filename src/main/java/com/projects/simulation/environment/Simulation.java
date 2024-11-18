package com.projects.simulation.environment;

import com.projects.simulation.action.init.PredatorSpawnAction;
import com.projects.simulation.action.init.RockSpawnAction;
import com.projects.simulation.action.init.TreeSpawnAction;
import com.projects.simulation.entity.inanimate.Rock;
import com.projects.simulation.utils.GameUtils;
import com.projects.simulation.action.Action;
import com.projects.simulation.action.init.GrassSpawnAction;
import com.projects.simulation.action.init.HerbivoreSpawnAction;
import com.projects.simulation.action.turn.MoveAction;
import com.projects.simulation.entity.animate.Herbivore;
import com.projects.simulation.entity.inanimate.Grass;
import com.projects.simulation.io.ConsoleManager;
import com.projects.simulation.render.WorldMapRender;
import com.projects.simulation.utils.MenuOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        initActions.add(new RockSpawnAction());
        initActions.add(new TreeSpawnAction());
        initActions.add(new PredatorSpawnAction());
        initActions.add(new HerbivoreSpawnAction());
    }

    public void startGame() {
        consoleManager.printWelcomeWords();
        createNewMap();
        executeGameLoop();
    }

    private void createNewMap() {
        this.worldMap = new WorldMap(GameUtils.WORLD_MAP_HEIGHT, GameUtils.WORLD_MAP_WIDTH);
        for (Action action : initActions) {
//            action.perform(worldMap);

            worldMap.setEntity(new Cell(9, 1), new Herbivore());

            worldMap.setEntity(new Cell(1, 10), new Grass());

            worldMap.setEntity(new Cell(1, 2), new Rock());
            worldMap.setEntity(new Cell(2, 2), new Rock());
            worldMap.setEntity(new Cell(3, 2), new Rock());
            worldMap.setEntity(new Cell(4, 2), new Rock());
            worldMap.setEntity(new Cell(5, 2), new Rock());
            worldMap.setEntity(new Cell(6, 2), new Rock());
            worldMap.setEntity(new Cell(7, 2), new Rock());
            worldMap.setEntity(new Cell(8, 2), new Rock());
            worldMap.setEntity(new Cell(9, 2), new Rock());
        }
    }

    private void executeGameLoop() {
        worldMapRender.renderMap(worldMap);
        consoleManager.printGameFeatures();
        processUserInput();
    }

    private void processUserInput() {
        while (true) {
            int userChoice = consoleManager.readUserInput();
            Optional<MenuOptions> selectedOption = MenuOptions.fromNumber(userChoice);

            if (selectedOption.isPresent()) {
                switch (selectedOption.get()) {
                    case OPTION_ONE_MOVE -> nextTurn();
                    case OPTION_ENDLESS_CYCLE -> startSimulation();
                    case OPTION_PAUSE_SIMULATION -> pauseSimulation();
                    case OPTION_NEW_MAP -> createNewMap();
                    case OPTION_EXIT -> {
                        endGame();
                        return;
                    }
                }
            } else {
                System.out.println("Invalid option. Please try again");
            }
        }
    }

    private void nextTurn() {
        MoveAction moveAction = new MoveAction();
        moveAction.perform(worldMap);
        executeGameLoop();
    }

    private void startSimulation() {

    }

    private void pauseSimulation() {

    }

    private void endGame() {
        consoleManager.printGoodByeWords();
    }
}
