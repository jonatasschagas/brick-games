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
    private long playerId;

    public ScoreLabel(long playerId) {
        font = new BitmapFont();
        this.playerId = playerId;
    }

    @Override
    public void render(LogicGame logicGame, SpriteBatch batch) {
        int score = logicGame.getScoreByPlayerId(playerId);
        int playerNumber = logicGame.getPlayerNumber(playerId);

        float x = GameConfiguration.SCREEN_WIDTH - GameConfiguration.TILE_SIZE * 3;
        float y = GameConfiguration.SCREEN_HEIGHT - GameConfiguration.TILE_SIZE * 2;
        if(playerNumber == 1) {
            font.draw(batch, "Player 1: " + score, x, y);
        } else {
            x = 5;
            font.draw(batch, "Player 2: " + score, x, y);
        }
        font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void dispose() {
        font.dispose();
    }

}
