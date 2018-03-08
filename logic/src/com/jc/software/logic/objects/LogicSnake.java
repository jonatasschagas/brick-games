package com.jc.software.logic.objects;

import com.jc.software.GameConfiguration;
import com.jc.software.logic.commands.LogicCommand;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.jc.software.GameConfiguration.*;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicSnake implements LogicGameObject {

    private int id;
    private Direction bufferedDirection;
    private int lastTick;
    private List<LogicSnakeSegment> snakeSegmentList;
    private long playerId;
    private int playerNumber;

    public LogicSnake() {}

    public LogicSnake(int id, long playerId, int playerNumber) {
        this.id = id;
        lastTick = 0;
        snakeSegmentList = new ArrayList<LogicSnakeSegment>();
        this.playerId = playerId;
        this.playerNumber = playerNumber;
        initialize();
    }

    private void initialize() {
        if (playerNumber == 1) {
            bufferedDirection = Direction.UP;
            snakeSegmentList.add(new LogicSnakeSegment(getNextSnakeSegmentId(), Direction.UP, GameConfiguration.SCREEN_WIDTH_IN_TILES / 2, 0));
        } else {
            bufferedDirection = Direction.DOWN;
            snakeSegmentList.add(new LogicSnakeSegment(getNextSnakeSegmentId(), Direction.DOWN, GameConfiguration.SCREEN_WIDTH_IN_TILES / 2 - 2, GameConfiguration.SCREEN_HEIGHT_IN_TILES));
        }
    }

    @Override
    public void executeCommand(LogicCommand command) {

        Direction newDirection = null;

        switch (command.getType()) {
            case LogicCommand.TURN_UP:
                newDirection  = Direction.UP;
                break;
            case LogicCommand.TURN_DOWN:
                newDirection  = Direction.DOWN;
                break;
            case LogicCommand.TURN_LEFT:
                newDirection  = Direction.LEFT;
                break;
            case LogicCommand.TURN_RIGHT:
                newDirection  = Direction.RIGHT;
                break;
        }

        if(!areDirectionsOpposite(newDirection, bufferedDirection)) {
            bufferedDirection = newDirection;
        }

    }

    public void increaseSnakeLength() {
        LogicSnakeSegment lastSegment = snakeSegmentList.get(snakeSegmentList.size() - 1);
        LogicSnakeSegment snakeSegment = new LogicSnakeSegment(getNextSnakeSegmentId(), lastSegment.getCurrentDirection(),
                lastSegment.getTileX(), lastSegment.getTileY());

        switch (snakeSegment.getCurrentDirection()) {
            case UP:
                snakeSegment.setTileY(snakeSegment.getTileY()- 1);
                break;
            case DOWN:
                snakeSegment.setTileY(snakeSegment.getTileY() + 1);
                break;
            case LEFT:
                snakeSegment.setTileX(snakeSegment.getTileX() + 1);
                break;
            case RIGHT:
                snakeSegment.setTileX(snakeSegment.getTileX() - 1);
                break;
        }
        snakeSegmentList.add(snakeSegment);
    }

    private int getNextSnakeSegmentId() {
        return GAME_ID_SNAKE_SEGMENT_START_RANGE + snakeSegmentList.size();
    }


    @Override
    public void update(int tick) {

        int deltaTick = tick - lastTick;

        if (deltaTick >= SNAKE_VELOCITY) {

            lastTick = tick;

            for (int i = snakeSegmentList.size() - 1; i >= 0; i--) {

                LogicSnakeSegment snakeSegment = snakeSegmentList.get(i);

                int nextTileX;
                int nextTileY;

                if (i == 0) {
                    nextTileX = getNextTileX(0);
                    nextTileY = getNextTileY(0);
                } else {
                    LogicSnakeSegment nextSeg = snakeSegmentList.get(i - 1);
                    nextTileX = nextSeg.getTileX();
                    nextTileY = nextSeg.getTileY();
                }

                snakeSegment.setTileX(nextTileX);
                snakeSegment.setTileY(nextTileY);

                // setting the limits
                if (snakeSegment.getTileY() > SCREEN_HEIGHT_IN_TILES) {
                    snakeSegment.setTileY(0);
                } else if (snakeSegment.getTileY() < 0) {
                    snakeSegment.setTileY(SCREEN_HEIGHT_IN_TILES);
                } else if (snakeSegment.getTileX() > SCREEN_WIDTH_IN_TILES) {
                    snakeSegment.setTileX(0);
                } else if (snakeSegment.getTileX() < 0) {
                    snakeSegment.setTileX(SCREEN_WIDTH_IN_TILES);
                }
            }
        }
    }

    private boolean areDirectionsOpposite(Direction d1, Direction d2) {
        return d1 == Direction.UP && d2 == Direction.DOWN || d1 == Direction.DOWN && d2 == Direction.UP
                || d1 == Direction.LEFT && d2 == Direction.RIGHT || d1 == Direction.RIGHT && d2 == Direction.LEFT;
    }

    private float lerp(float v0, float v1, float t) {
        return (1 - t) * v0 + t * v1;
    }

   /* public float getX(int segmentIndex) {
        return lerp(snakeSegmentList.get(segmentIndex).getTileX() * TILE_SIZE, getNextTileX(segmentIndex) * TILE_SIZE, currentWeight);
    }

    public float getY(int segmentIndex) {
        return lerp(snakeSegmentList.get(segmentIndex).getTileY() * TILE_SIZE, getNextTileY(segmentIndex) * TILE_SIZE, currentWeight);
    }*/

    private int getNextTileX(int segmentIndex) {
        int nextTileX = snakeSegmentList.get(segmentIndex).getTileX();
        switch (bufferedDirection) {
            case LEFT:
                nextTileX--;
                break;
            case RIGHT:
                nextTileX++;
                break;
        }
        return nextTileX;
    }

    private int getNextTileY(int segmentIndex) {
        int nextTileY = snakeSegmentList.get(segmentIndex).getTileY();
        switch (bufferedDirection) {
            case UP:
                nextTileY++;
                break;
            case DOWN:
                nextTileY--;
                break;
        }
        return nextTileY;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getTileX() {
        return snakeSegmentList.get(0).getTileX();
    }

    @Override
    public int getTileY() {
        return snakeSegmentList.get(0).getTileY();
    }

    public List<LogicSnakeSegment> getSnakeSegmentList() {
        return snakeSegmentList;
    }

    public LogicSnakeSegment getHead() {
        return snakeSegmentList.get(0);
    }

    public Direction getBufferedDirection() {
        return bufferedDirection;
    }

    public long getPlayerId() {
        return playerId;
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }


    @Override
    public void encode(ByteBuffer byteBuffer) {
        byteBuffer.putInt(id);
        byteBuffer.putInt(bufferedDirection.ordinal());
        byteBuffer.putInt(lastTick);
        byteBuffer.putInt(playerNumber);
        byteBuffer.putLong(playerId);
        byteBuffer.putInt(snakeSegmentList != null ? snakeSegmentList.size(): 0);
        for(LogicSnakeSegment snakeSegment: snakeSegmentList) {
            snakeSegment.encode(byteBuffer);
        }
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        id = byteBuffer.getInt();
        bufferedDirection = Direction.values()[byteBuffer.getInt()];
        lastTick = byteBuffer.getInt();
        playerNumber = byteBuffer.getInt();
        playerId = byteBuffer.getLong();
        int snakeSegmentListSize = byteBuffer.getInt();
        if(snakeSegmentList == null) {
            snakeSegmentList = new ArrayList<>();
        }
        for(int i = 0; i < snakeSegmentListSize; i++) {
            LogicSnakeSegment snakeSegment;
            if(i > snakeSegmentList.size() - 1) {
                snakeSegment = new LogicSnakeSegment();
                snakeSegment.decode(byteBuffer);
                snakeSegmentList.add(snakeSegment);
            } else {
                snakeSegment = snakeSegmentList.get(i);
                snakeSegment.decode(byteBuffer);
            }
        }
    }

}
