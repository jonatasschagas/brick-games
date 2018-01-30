package com.jc.software.logic.objects;

import com.jc.software.GameConfiguration;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.commands.LogicIncreaseSnakeLengthCommand;
import com.jc.software.logic.commands.LogicUpdateSnakePieceCommand;
import com.jc.software.logic.commands.controls.LogicRestartGameCommand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicGame implements LogicGameObject {

    private List<LogicGameObject> gameObjects;
    private LogicSnake logicSnake;
    private LogicSnakePiece logicSnakePiece;
    private int score;
    private GameState gameState;
    private transient Random random;

    public LogicGame() {
        random = new Random();
        reset();
    }

    @Override
    public void executeCommand(LogicCommand command) {
        if (command instanceof LogicRestartGameCommand && gameState == GameState.GAME_OVER) {
            reset();
            return;
        }

        Iterator<LogicGameObject> gameObjectIterator = gameObjects.iterator();
        while (gameObjectIterator.hasNext()) {
            LogicGameObject gameObject = gameObjectIterator.next();
            // only passes the commands to the right target objects
            if (command.getTargetId() == gameObject.getId()) {
                gameObject.executeCommand(command);
            }
        }
    }

    @Override
    public void update(float deltaTime) {

        if (gameState == GameState.GAME_OVER) {
            return;
        }

        for (LogicGameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);
        }

        // check collision with snake piece
        LogicSnakeSegment head = logicSnake.getHead();
        if (collide(head.getCurrentTileX(), head.getCurrentTileY(), logicSnakePiece.getCurrentTileX(), logicSnakePiece.getCurrentTileY())) {
            // got the snake piece
            executeCommand(new LogicIncreaseSnakeLengthCommand(logicSnake.getId()));
            executeCommand(new LogicUpdateSnakePieceCommand(logicSnakePiece.getId(), generateRandomTileXCoordinate(), generateRandomTileYCoordinate()));
            score++;
        }

        // check if the segments of the snake collided
        for (LogicSnakeSegment s : logicSnake.getSnakeSegmentList()) {
            if (head != s && collide(head.getCurrentTileX(), head.getCurrentTileY(), s.getCurrentTileX(), s.getCurrentTileY())) {
                gameState = GameState.GAME_OVER;
                return;
            }
        }

    }

    private void reset() {
        gameObjects = new ArrayList<LogicGameObject>();
        logicSnake = new LogicSnake(this);
        int id = random.nextInt();
        logicSnakePiece = new LogicSnakePiece(id, generateRandomTileXCoordinate(), generateRandomTileYCoordinate());
        gameObjects.add(logicSnake);
        gameObjects.add(logicSnakePiece);
        score = 0;
        gameState = GameState.RUNNING;
    }

    private boolean collide(int xa, int ya, int xb, int yb) {
        return xa == xb && ya == yb;
    }

    private int generateRandomTileXCoordinate() {
        return random.nextInt(GameConfiguration.SCREEN_WIDTH_IN_TILES - 1);
    }

    private int generateRandomTileYCoordinate() {
        return random.nextInt(GameConfiguration.SCREEN_HEIGHT_IN_TILES - 1);
    }

    public int getNextRandomInt() {
        return random.nextInt();
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public float getWidth() {
        return GameConfiguration.SCREEN_WIDTH;
    }

    @Override
    public float getHeight() {
        return GameConfiguration.SCREEN_HEIGHT;
    }

    public LogicSnake getLogicSnake() {
        return logicSnake;
    }

    public LogicSnakePiece getLogicSnakePiece() {
        return logicSnakePiece;
    }

    public int getScore() {
        return score;
    }

    public GameState getGameState() {
        return gameState;
    }

    public enum GameState {
        RUNNING,
        GAME_OVER
    }
}
