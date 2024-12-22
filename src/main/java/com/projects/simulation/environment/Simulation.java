package com.projects.simulation.environment;

import com.projects.simulation.action.Action;
import com.projects.simulation.action.init.*;
import com.projects.simulation.action.turn.MoveAction;
import com.projects.simulation.entity.EntityType;
import com.projects.simulation.io.ConsoleReader;
import com.projects.simulation.io.ConsoleWriter;
import com.projects.simulation.render.WorldMapRender;
import com.projects.simulation.utils.GameUtils;
import com.projects.simulation.utils.MenuOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Simulation {

    private Integer moveCount;
    private WorldMap worldMap;
    private volatile boolean isRunningSimulation;
    private boolean isRunningGame;
    private final WorldMapRender worldMapRender;
    private final List<Action> initActions;

    public Simulation() {
        this.moveCount = 0;
        this.isRunningSimulation = false;
        this.isRunningGame = false;
        this.worldMapRender = new WorldMapRender();
        this.initActions = new ArrayList<>();
        generateInitActions();
        createNewMap();
    }

    private void generateInitActions() {
        initActions.add(new GrassSpawnAction());
        initActions.add(new RockSpawnAction());
        initActions.add(new TreeSpawnAction());
        initActions.add(new PredatorSpawnAction());
        initActions.add(new HerbivoreSpawnAction());
    }

    private void createNewMap() {
        this.moveCount = 0;
        this.worldMap = new WorldMap(GameUtils.WORLD_MAP_HEIGHT, GameUtils.WORLD_MAP_WIDTH);
        for (Action action : initActions) {
            action.perform(worldMap);
        }
    }

    public void startGame() {
        BlockingQueue<Integer> queueUserInputs = new ArrayBlockingQueue<>(10);

        ConsoleReader consoleReader = new ConsoleReader(queueUserInputs);
        Thread inputReadingThread = new Thread(consoleReader);
        inputReadingThread.setDaemon(true);
        inputReadingThread.start();

        printGreeting();
        this.isRunningGame = true;
        while (isRunningGame) {
            processGameExecution(queueUserInputs);
        }
    }

    private void processGameExecution(BlockingQueue<Integer> queueUserInputs) {
        Optional<MenuOptions> optionalSelectedMenuOption = processUserInput(queueUserInputs);
        if (optionalSelectedMenuOption.isPresent()) {
            MenuOptions options = optionalSelectedMenuOption.get();
            switch (options) {
                case OPTION_ONE_MOVE -> {
                    nextTurn();
                    updateUI();
                }
                case OPTION_ENDLESS_SIMULATION -> startEndlessSimulation(queueUserInputs);
                case OPTION_EXTINCTION_SIMULATION -> startExtinctionSimulation(queueUserInputs);
                case OPTION_NEW_MAP -> {
                    createNewMap();
                    updateUI();
                }
                case OPTION_PAUSE_SIMULATION -> pauseSimulation();
                case OPTION_EXIT -> endGame();
            }
        } else {
            ConsoleWriter.printMessage("You have selected an incorrect menu option");
        }
    }

    private void nextTurn() {
        MoveAction moveAction = new MoveAction();
        moveAction.perform(worldMap);
        moveCount++;
    }

    private void startEndlessSimulation(BlockingQueue<Integer> queueInput) {
        this.isRunningSimulation = true;
        while (isSimulationActive(queueInput)) {
            if (needHerbivoreRegeneration()) {
                generateHerbivores();
            }
            nextTurn();
            updateUI();
        }
    }

    private void startExtinctionSimulation(BlockingQueue<Integer> queueInput) {
        this.isRunningSimulation = true;
        while (isSimulationActive(queueInput)) {
            if (canContinueSimulation()) {
                nextTurn();
                updateUI();
            } else {
                ConsoleWriter.printMessage("The simulation can no longer continue on this map");
                break;
            }
        }
    }

    private void pauseSimulation() {
        if (!isRunningSimulation) {
            ConsoleWriter.printMessage("The Simulation isn't active");
        } else {
            this.isRunningSimulation = false;
            ConsoleWriter.printMessage("Simulation paused. Choose an option to continue.");
        }
    }

    private void endGame() {
        ConsoleWriter.printGoodByeWords();
        this.isRunningSimulation = false;
        this.isRunningGame = false;
    }

    private Optional<MenuOptions> processUserInput(BlockingQueue<Integer> queueUserInputs) {
        int userChoice;
        try {
            userChoice = queueUserInputs.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return MenuOptions.fromNumber(userChoice);
    }

    private void generateHerbivores() {
        HerbivoreSpawnAction spawnAction = new HerbivoreSpawnAction();
        spawnAction.perform(this.worldMap);
    }

    private boolean needHerbivoreRegeneration() {
        return worldMap.getNumberOfEntity(EntityType.HERBIVORE) == 0;
    }

    private boolean isSimulationActive(BlockingQueue<Integer> queue) {
        return isRunningSimulation && queue.isEmpty();
    }

    private boolean canContinueSimulation() {
        return worldMap.isEntityTypePresent(EntityType.PREDATOR) &&
                worldMap.isEntityTypePresent(EntityType.HERBIVORE);
    }

    private void updateUI() {
        ConsoleWriter.printMessage("Number of moves: " + moveCount);
        worldMapRender.renderMap(worldMap);
        ConsoleWriter.printGameFeatures();
        sleep(1000);
    }

    private void printGreeting() {
        ConsoleWriter.printWelcomeWords();
        worldMapRender.renderMap(worldMap);
        ConsoleWriter.printGameFeatures();
    }

    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
