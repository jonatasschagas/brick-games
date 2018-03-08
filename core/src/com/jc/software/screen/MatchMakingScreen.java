package com.jc.software.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jc.software.BrickGames;
import com.jc.software.GameConfiguration;
import com.jc.software.ai.OpponentAI;
import com.jc.software.remote.GameServerHandler;
import com.jc.software.remote.MockGameServer;
import com.jc.software.remote.RemoteGameServer;

import java.util.Random;

/**
 * Created by jonataschagas on 08/02/18.
 */
public class MatchMakingScreen implements Screen {

    private final BrickGames game;
    private OrthographicCamera camera;
    private GameServerHandler gameServerHandler;
    private OpponentAI opponentAI;

    public MatchMakingScreen(final BrickGames game) {
        this.game = game;
        initialize();
    }

    private void initialize() {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, GameConfiguration.SCREEN_HEIGHT, GameConfiguration.SCREEN_WIDTH);
        // right now only single player available
        createServerConnection(false);
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
        font.draw(batch, "Looking for opponent...", GameConfiguration.SCREEN_WIDTH / 2 - 50, GameConfiguration.SCREEN_HEIGHT / 2);
        batch.end();

        if (gameServerHandler.hasGameStarted()) {
            game.setScreen(new GameScreen(game, gameServerHandler, opponentAI));
            dispose();
        }

    }

    private void createServerConnection(boolean multiPlayer) {
        Random random = new Random();
        long playerId = random.nextInt(10000);
        if (multiPlayer) {
            // TODO:: IMPLEMENT WITH NETTY
        } else {
            RemoteGameServer gameServer = MockGameServer.getInstance();
            this.gameServerHandler = new GameServerHandler(playerId, gameServer);
            this.opponentAI = OpponentAI.getOpponentAI();
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
