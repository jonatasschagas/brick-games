package com.jc.software.logic.objects;

/**
 * Created by jonataschagas on 28/01/18.
 */
public class LogicSnakeSegment {

    private LogicSnake.Direction currentDirection;
    private int currentTileX;
    private int currentTileY;
    private float x;
    private float y;

    public LogicSnakeSegment(LogicSnake.Direction currentDirection, int currentTileX, int currentTileY) {
        this.currentDirection = currentDirection;
        this.currentTileX = currentTileX;
        this.currentTileY = currentTileY;
    }

    public LogicSnake.Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(LogicSnake.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public int getCurrentTileX() {
        return currentTileX;
    }

    public void setCurrentTileX(int currentTileX) {
        this.currentTileX = currentTileX;
    }

    public int getCurrentTileY() {
        return currentTileY;
    }

    public void setCurrentTileY(int currentTileY) {
        this.currentTileY = currentTileY;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
