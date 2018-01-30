package com.jc.software.logic.objects;

import com.jc.software.GameConfiguration;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.commands.LogicUpdateSnakePieceCommand;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicSnakePiece implements LogicGameObject {

    private float x;
    private float y;
    private int id;

    public LogicSnakePiece(int id, float x, float y) {
        this.id = id;
        this.x = x * GameConfiguration.TILE_SIZE;
        this.y = y * GameConfiguration.TILE_SIZE;
    }

    @Override
    public void executeCommand(LogicCommand command) {
        if (command.getType() == LogicCommand.UPDATE_SNAKE_PIECE) {
            LogicUpdateSnakePieceCommand updateSnakePieceCommand = (LogicUpdateSnakePieceCommand) command;
            x = updateSnakePieceCommand.getX() * GameConfiguration.TILE_SIZE;
            y = updateSnakePieceCommand.getY() * GameConfiguration.TILE_SIZE;
        }
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return GameConfiguration.TILE_SIZE;
    }

    @Override
    public float getHeight() {
        return GameConfiguration.TILE_SIZE;
    }

    public int getCurrentTileX() {
        return Math.round(x / GameConfiguration.TILE_SIZE);
    }

    public int getCurrentTileY() {
        return Math.round(y / GameConfiguration.TILE_SIZE);
    }


}
