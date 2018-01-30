package com.jc.software.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by jonataschagas on 25/01/18.
 */
public interface GameObject {

    void render(SpriteBatch batch);

    void dispose();

}
