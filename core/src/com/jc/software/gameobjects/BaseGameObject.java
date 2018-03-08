package com.jc.software.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.jc.software.logic.objects.LogicGame;

/**
 * Created by jonataschagas on 25/01/18.
 */
public abstract class BaseGameObject implements GameObject {

    private Texture texture;
    private Rectangle rectangle;

    public BaseGameObject(String textureName, float x, float y) {
        this.texture = new Texture(textureName);
        rectangle = new Rectangle();
        rectangle.x = x;
        rectangle.y = y;
        rectangle.width = texture.getWidth();
        rectangle.height = texture.getHeight();
    }

    public Texture getTexture() {
        return texture;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setX(float x) {
        rectangle.x = x;
    }

    public void setY(float y) {
        rectangle.y = y;
    }

    @Override
    public void render(LogicGame logicGame, SpriteBatch batch) {
        render(logicGame);
        batch.draw(texture, rectangle.getX(), rectangle.getY());
    }

    public abstract void render(LogicGame logicGame);

    public void dispose() {
        texture.dispose();
    }

}
