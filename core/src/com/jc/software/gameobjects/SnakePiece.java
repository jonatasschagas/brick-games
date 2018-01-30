package com.jc.software.gameobjects;

import com.jc.software.logic.objects.LogicGame;
import com.jc.software.logic.objects.LogicSnakePiece;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class SnakePiece extends BaseGameObject {

    private LogicGame logicGame;

    public SnakePiece(LogicGame logicGame) {
        super("square_blue.png", logicGame.getLogicSnakePiece().getX(), logicGame.getLogicSnakePiece().getY());
        this.logicGame = logicGame;
    }

    public void render() {
        LogicSnakePiece snakePiece = logicGame.getLogicSnakePiece();
        setX(snakePiece.getX());
        setY(snakePiece.getY());
    }

}
