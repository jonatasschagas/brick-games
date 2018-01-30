package com.jc.software.logic.commands;

/**
 * Created by jonataschagas on 26/01/18.
 */
public class LogicUpdateSnakePieceCommand implements LogicCommand {

    private int id;
    private float x;
    private float y;

    public LogicUpdateSnakePieceCommand(int targetId, float x, float y) {
        this.id = targetId;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getType() {
        return LogicCommand.UPDATE_SNAKE_PIECE;
    }

    @Override
    public int getTargetId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
