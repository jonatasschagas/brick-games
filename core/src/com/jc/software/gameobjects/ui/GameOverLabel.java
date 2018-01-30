package com.jc.software.gameobjects.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jc.software.GameConfiguration;
import com.jc.software.gameobjects.GameObject;
import com.jc.software.logic.objects.LogicGame;

/**
 * Created by jonataschagas on 29/01/18.
 */
public class GameOverLabel implements GameObject {

    private BitmapFont font;
    private LogicGame logicGame;
    private float x;
    private float y;

    public GameOverLabel(LogicGame logicGame) {
        font = new BitmapFont();
        this.logicGame = logicGame;
        this.x = GameConfiguration.SCREEN_WIDTH / 2;
        this.y = GameConfiguration.SCREEN_HEIGHT / 2;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (logicGame.getGameState() == LogicGame.GameState.GAME_OVER) {
            font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            font.draw(batch, "Game Over! Press enter to restart.", x, y);
        }
    }

    @Override
    public void dispose() {
        font.dispose();
    }

}
