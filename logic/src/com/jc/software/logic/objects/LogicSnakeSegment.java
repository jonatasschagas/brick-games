package com.jc.software.logic.objects;

import com.jc.software.GameConfiguration;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.util.Utils;

import java.nio.ByteBuffer;

/**
 * Created by jonataschagas on 28/01/18.
 */
public class LogicSnakeSegment implements LogicGameObject {

    private LogicSnake.Direction currentDirection;
    private int tileX;
    private int tileY;
    private int id;

    public LogicSnakeSegment() {}

    public LogicSnakeSegment(int id, LogicSnake.Direction currentDirection, int currentTileX, int currentTileY) {
        this.currentDirection = currentDirection;
        this.tileX = currentTileX;
        this.tileY = currentTileY;
        this.id = id;
    }

    public LogicSnake.Direction getCurrentDirection() {
        return currentDirection;
    }

    @Override
    public void executeCommand(LogicCommand command) {}

    @Override
    public void update(int tick) {}

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    @Override
    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    @Override
    public void encode(ByteBuffer byteBuffer) {
        byteBuffer.putInt(currentDirection.ordinal());
        long location = Utils.asLong(tileX, tileY);
        byteBuffer.putLong(location);
        byteBuffer.putInt(id);
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        currentDirection = LogicSnake.Direction.values()[byteBuffer.getInt()];
        long location = byteBuffer.getLong();
        tileX = Utils.getX(location);
        tileY = Utils.getY(location);
        id = byteBuffer.getInt();
    }

}
