package com.jc.software.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jc.software.logic.objects.LogicGame;

/**
 * Created by jonataschagas on 25/01/18.
 */
public interface GameObject {

    void render(LogicGame logicGame, SpriteBatch batch);

    void dispose();

}
