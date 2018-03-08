package com.jc.software.logic.objects;

import com.jc.software.GameConfiguration;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.util.Utils;

import java.nio.ByteBuffer;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicSnakePiece implements LogicGameObject {

    private int tileX;
    private int tileY;
    private int id;

    public LogicSnakePiece() {
        this.id = GameConfiguration.GAME_ID_SNAKE_PIECE;
    }

    @Override
    public void executeCommand(LogicCommand command) {
    }

    public void updateSnakePiece(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    @Override
    public void update(int tick) {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getTileX() {
        return tileX;
    }

    @Override
    public int getTileY() {
        return tileY;
    }

    @Override
    public void encode(ByteBuffer byteBuffer) {
        long location = Utils.asLong(tileX, tileY);
        byteBuffer.putLong(location);
        byteBuffer.putInt(id);
    }

    @Override
    public void decode(ByteBuffer byteBuffer) {
        long location = byteBuffer.getLong();
        tileX = Utils.getX(location);
        tileY = Utils.getY(location);
        id = byteBuffer.getInt();
    }

}
