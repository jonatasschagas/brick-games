package com.jc.software.logic.objects;

import com.jc.software.GameConfiguration;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.commands.LogicIncreaseSnakeLengthCommand;

import java.util.ArrayList;
import java.util.List;

import static com.jc.software.GameConfiguration.*;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicSnake implements LogicGameObject {

    private int id;
    private Direction bufferedDirection;
    private float currentTime;
    private LogicGame logicGame;
    private List<LogicSnakeSegment> snakeSegmentList;
    private LogicSnakeSegment head;

    public LogicSnake(LogicGame logicGame) {
        id = logicGame.getNextRandomInt();
        bufferedDirection = Direction.UP;
        currentTime = 0;
        this.logicGame = logicGame;
        snakeSegmentList = new ArrayList<LogicSnakeSegment>();
        snakeSegmentList.add(new LogicSnakeSegment(Direction.UP, GameConfiguration.SCREEN_WIDTH_IN_TILES / 2, 0));
        head = snakeSegmentList.get(0);
    }

    @Override
    public void executeCommand(LogicCommand command) {
        switch (command.getType()) {
            case LogicCommand.TURN_UP:
                bufferedDirection = Direction.UP;
                break;
            case LogicCommand.TURN_DOWN:
                bufferedDirection = Direction.DOWN;
                break;
            case LogicCommand.TURN_LEFT:
                bufferedDirection = Direction.LEFT;
                break;
            case LogicCommand.TURN_RIGHT:
                bufferedDirection = Direction.RIGHT;
                break;
            case LogicIncreaseSnakeLengthCommand.INCREASE_SNAKE_LENGTH:

                LogicSnakeSegment lastSegment = snakeSegmentList.get(snakeSegmentList.size() - 1);
                LogicSnakeSegment snakeSegment = new LogicSnakeSegment(lastSegment.getCurrentDirection(),
                        lastSegment.getCurrentTileX(), lastSegment.getCurrentTileY());

                switch (snakeSegment.getCurrentDirection()) {
                    case UP:
                        snakeSegment.setCurrentTileY(snakeSegment.getCurrentTileY() - 1);
                        break;
                    case DOWN:
                        snakeSegment.setCurrentTileY(snakeSegment.getCurrentTileY() + 1);
                        break;
                    case LEFT:
                        snakeSegment.setCurrentTileX(snakeSegment.getCurrentTileX() + 1);
                        break;
                    case RIGHT:
                        snakeSegment.setCurrentTileX(snakeSegment.getCurrentTileX() - 1);
                        break;
                }
                snakeSegment.setX(snakeSegment.getCurrentTileX() * TILE_SIZE);
                snakeSegment.setY(snakeSegment.getCurrentTileY() * TILE_SIZE);
                snakeSegmentList.add(snakeSegment);
                break;
        }
    }

    @Override
    public void update(float deltaTime) {

        float weight = currentTime / TIME_PER_TILE;

        for (LogicSnakeSegment snakeSegment : snakeSegmentList) {

            int nextTileX = snakeSegment.getCurrentTileX();
            int nextTileY = snakeSegment.getCurrentTileY();

            switch (snakeSegment.getCurrentDirection()) {
                case UP:
                    nextTileY++;
                    break;
                case DOWN:
                    nextTileY--;
                    break;
                case LEFT:
                    nextTileX--;
                    break;
                case RIGHT:
                    nextTileX++;
                    break;
            }

            float sX = snakeSegment.getCurrentTileX() * TILE_SIZE;
            float sY = snakeSegment.getCurrentTileY() * TILE_SIZE;
            float tX = nextTileX * TILE_SIZE;
            float tY = nextTileY * TILE_SIZE;

            snakeSegment.setX(lerp(sX, tX, weight));
            snakeSegment.setY(lerp(sY, tY, weight));

            // setting the limits
            if (snakeSegment.getCurrentTileY() > SCREEN_HEIGHT_IN_TILES) {
                snakeSegment.setCurrentTileY(0);
            } else if (snakeSegment.getCurrentTileY() < 0) {
                snakeSegment.setCurrentTileY(SCREEN_HEIGHT_IN_TILES);
            } else if (snakeSegment.getCurrentTileX() > SCREEN_WIDTH_IN_TILES) {
                snakeSegment.setCurrentTileX(0);
            } else if (snakeSegment.getCurrentTileX() < 0) {
                snakeSegment.setCurrentTileX(SCREEN_WIDTH_IN_TILES);
            }

        }

        if (weight > 1) {

            currentTime = 0;

            for (LogicSnakeSegment snakeSegment : snakeSegmentList) {

                switch (snakeSegment.getCurrentDirection()) {
                    case UP:
                        snakeSegment.setCurrentTileY(snakeSegment.getCurrentTileY() + 1);
                        break;
                    case DOWN:
                        snakeSegment.setCurrentTileY(snakeSegment.getCurrentTileY() - 1);
                        break;
                    case LEFT:
                        snakeSegment.setCurrentTileX(snakeSegment.getCurrentTileX() - 1);
                        break;
                    case RIGHT:
                        snakeSegment.setCurrentTileX(snakeSegment.getCurrentTileX() + 1);
                        break;
                }
            }

            for (int i = snakeSegmentList.size() - 1; i > 0; i--) {
                LogicSnakeSegment currentSegment = snakeSegmentList.get(i);
                LogicSnakeSegment nextSegment = snakeSegmentList.get(i - 1);
                currentSegment.setCurrentDirection(nextSegment.getCurrentDirection());
            }

            // don't allow the snake to turn to opposite directions
            if (bufferedDirection != head.getCurrentDirection() && !areDirectionsOpposite(bufferedDirection, head.getCurrentDirection())) {
                head.setCurrentDirection(bufferedDirection);
            }
        }

        currentTime += deltaTime;

    }

    private boolean areDirectionsOpposite(Direction d1, Direction d2) {
        return d1 == Direction.UP && d2 == Direction.DOWN || d1 == Direction.DOWN && d2 == Direction.UP
                || d1 == Direction.LEFT && d2 == Direction.RIGHT || d1 == Direction.RIGHT && d2 == Direction.LEFT;
    }

    private float lerp(float v0, float v1, float t) {
        return (1 - t) * v0 + t * v1;
    }

    @Override
    public int getId() {
        return id;
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
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    public List<LogicSnakeSegment> getSnakeSegmentList() {
        return snakeSegmentList;
    }

    public LogicSnakeSegment getHead() {
        return head;
    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
