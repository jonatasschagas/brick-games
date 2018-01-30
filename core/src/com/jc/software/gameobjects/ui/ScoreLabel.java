package com.jc.software.gameobjects.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jc.software.GameConfiguration;
import com.jc.software.gameobjects.GameObject;
import com.jc.software.logic.objects.LogicGame;

/**
 * Created by jonataschagas on 29/01/18.
 */
public class ScoreLabel implements GameObject {

    private BitmapFont font;
    private LogicGame logicGame;
    private float x;
    private float y;

    public ScoreLabel(LogicGame logicGame) {
        font = new BitmapFont();
        this.logicGame = logicGame;
        this.x = GameConfiguration.SCREEN_WIDTH - GameConfiguration.TILE_SIZE * 3;
        this.y = GameConfiguration.SCREEN_HEIGHT - GameConfiguration.TILE_SIZE * 2;
    }

    @Override
    public void render(SpriteBatch batch) {
        int score = logicGame.getScore();
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        font.draw(batch, "Score: " + score, x, y);
    }

    @Override
    public void dispose() {
        font.dispose();
    }

}
