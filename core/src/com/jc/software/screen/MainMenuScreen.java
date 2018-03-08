package com.jc.software.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jc.software.BrickGames;
import com.jc.software.GameConfiguration;

/**
 * Created by jonataschagas on 08/02/18.
 */
public class MainMenuScreen implements Screen {

    private final BrickGames game;
    private OrthographicCamera camera;

    public MainMenuScreen(final BrickGames game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, GameConfiguration.SCREEN_HEIGHT, GameConfiguration.SCREEN_WIDTH);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Batch batch = game.getBatch();
        BitmapFont font = game.getFont();

        camera.update();
        batch.begin();
        font.draw(batch, "Snake game!", GameConfiguration.SCREEN_WIDTH/2 - 50, GameConfiguration.SCREEN_HEIGHT/2);
        font.draw(batch, "Tap anywhere to begin!", GameConfiguration.SCREEN_WIDTH/2 - 50, GameConfiguration.SCREEN_HEIGHT/2 - 50);
        batch.end();

        if(Gdx.input.isTouched()) {
            game.setScreen(new MatchMakingScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
