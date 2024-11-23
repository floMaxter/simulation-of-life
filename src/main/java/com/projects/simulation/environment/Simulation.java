package com.projects.simulation.environment;

import com.projects.simulation.action.Action;
import com.projects.simulation.action.init.GrassSpawnAction;
import com.projects.simulation.action.init.HerbivoreSpawnAction;
import com.projects.simulation.action.init.PredatorSpawnAction;
import com.projects.simulation.action.init.RockSpawnAction;
import com.projects.simulation.action.init.TreeSpawnAction;
import com.projects.simulation.action.turn.MoveAction;
import com.projects.simulation.io.ConsoleManager;
import com.projects.simulation.render.WorldMapRender;
import com.projects.simulation.utils.GameUtils;
import com.projects.simulation.utils.MenuOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Simulation {

    private Integer moveCount;
    private boolean isSimulationRunning;
    private WorldMap worldMap;
    private final WorldMapRender worldMapRender;
    private final List<Action> initActions;

    public Simulation() {
        this.moveCount = 0;
        isSimulationRunning = false;
        this.worldMapRender = new WorldMapRender();
        this.initActions = new ArrayList<>();
        generateInitActions();
    }

    private void generateInitActions() {
        initActions.add(new GrassSpawnAction());
        initActions.add(new RockSpawnAction());
        initActions.add(new TreeSpawnAction());
        initActions.add(new PredatorSpawnAction());
        initActions.add(new HerbivoreSpawnAction());
    }

    public void startGame() {
        ConsoleManager.printWelcomeWords();
        createNewMap();

        boolean isRunning = true;
        while (isRunning) {
            updateUI();

            int userChoice = ConsoleManager.readUserInput();
            Optional<MenuOptions> selectedOption = MenuOptions.fromNumber(userChoice);
            if (selectedOption.isPresent()) {
                isRunning = processUserInput(selectedOption.get());
            } else {
                ConsoleManager.printInfo("Invalid option. Please try again");
            }
        }
    }

    private void createNewMap() {
        this.worldMap = new WorldMap(GameUtils.WORLD_MAP_HEIGHT, GameUtils.WORLD_MAP_WIDTH);
        for (Action action : initActions) {
            action.perform(worldMap);
        }
    }

    private void updateUI() {
        ConsoleManager.printInfo("Number of moves: " + moveCount);
        ConsoleManager.printNumberOfEntities(worldMap);
        worldMapRender.renderMap(worldMap);
        ConsoleManager.printGameFeatures();
    }

    private boolean processUserInput(MenuOptions options) {
        switch (options) {
            case OPTION_ONE_MOVE -> nextTurn();
            case OPTION_ENDLESS_SIMULATION -> startSimulation();
            case OPTION_PAUSE_SIMULATION -> pauseSimulation();
            case OPTION_NEW_MAP -> createNewMap();
            case OPTION_EXIT -> {
                endGame();
                return false;
            }
        }
        return true;
    }

    private void nextTurn() {
        MoveAction moveAction = new MoveAction();
        moveAction.perform(worldMap);
        moveCount++;
    }

    private void startSimulation() {
        while (true) {
            try {
                Thread.sleep(1000);
                nextTurn();
                updateUI();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void pauseSimulation() {
        ConsoleManager.printInfo("Simulation paused. Choose an option to continue.");
        isSimulationRunning = false;
    }

    private void endGame() {
        ConsoleManager.printGoodByeWords();
    }
}
