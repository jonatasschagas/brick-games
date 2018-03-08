package com.jc.software.gameobjects;

import com.jc.software.GameConfiguration;
import com.jc.software.logic.objects.LogicGame;
import com.jc.software.logic.objects.LogicSnakePiece;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class SnakePiece extends BaseGameObject {

    public SnakePiece() {
        super("square_blue.png", 0, 0);
    }

    public void render(LogicGame logicGame) {
        LogicSnakePiece snakePiece = logicGame.getLogicSnakePiece();
        setX(snakePiece.getTileX() * GameConfiguration.TILE_SIZE);
        setY(snakePiece.getTileY() * GameConfiguration.TILE_SIZE);
    }

}
