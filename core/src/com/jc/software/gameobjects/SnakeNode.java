package com.jc.software.gameobjects;

import com.jc.software.logic.objects.LogicGame;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class SnakeNode extends BaseGameObject {

    public SnakeNode(float x, float y, int playerNumber) {
        super(playerNumber == 1 ? "square.png" : "square2.png", x, y);
    }

    public void render(LogicGame logicGame) {}

}
