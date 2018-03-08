package com.jc.software.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jc.software.BrickGames;
import com.jc.software.ai.OpponentAI;
import com.jc.software.gameobjects.GameObject;
import com.jc.software.gameobjects.Snake;
import com.jc.software.gameobjects.SnakePiece;
import com.jc.software.gameobjects.ui.GameOverLabel;
import com.jc.software.gameobjects.ui.ScoreLabel;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.commands.LogicCommandFactory;
import com.jc.software.logic.objects.LogicGame;
import com.jc.software.logic.objects.LogicSnake;
import com.jc.software.remote.GameServerHandler;

import java.util.ArrayList;
import java.util.List;

import static com.jc.software.GameConfiguration.*;

public class GameScreen implements Screen {

    private final BrickGames game;
    private SpriteBatch batch;
    private List<GameObject> gameObjectList;
    private long beginTime;
    private long timeDiff;
    private long sleepTime;
    private GameServerHandler gameServerHandler;
    private OpponentAI opponentAI;
    private Thread opponentAIThread;

    public GameScreen(BrickGames game, GameServerHandler gameServerHandler, OpponentAI opponentAI) {
        this.game = game;
        this.gameServerHandler = gameServerHandler;
        this.opponentAI = opponentAI;
        initialize();
    }

    private void initialize() {
        this.batch = game.getBatch();
        LogicGame logicGame = gameServerHandler.getGameState();

        gameObjectList = new ArrayList<GameObject>();
        // GameScreen Elements
        gameObjectList.add(new Snake(logicGame.getPlayer1Id(), 1));
        gameObjectList.add(new Snake(logicGame.getPlayer2Id(), 2));
        gameObjectList.add(new SnakePiece());

        // UI
        gameObjectList.add(new ScoreLabel(logicGame.getPlayer1Id()));
        gameObjectList.add(new ScoreLabel(logicGame.getPlayer2Id()));
        gameObjectList.add(new GameOverLabel());

        beginTime = 0;
        sleepTime = 0;
        timeDiff = 0;
        if (opponentAI != null) {
            opponentAIThread = new Thread(opponentAI);
            opponentAIThread.setName("Opponent-thread");
            opponentAIThread.start();
        }
    }

    private void readInputAndUpdateGraphics() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        handleInput();
        updateGraphics();
        batch.end();
    }

    private void update() {
        // calculating how many ticks to advance in the game
        long deltaMs = System.currentTimeMillis() - gameServerHandler.getGameState().getStartGameMillis();
        int ticksToNow = (int) ((deltaMs * FPS) / 1000);
        int ticksToAdvance = ticksToNow - gameServerHandler.getGameState().getTick();
        for (int i = 0; i < ticksToAdvance; i++) {
            gameServerHandler.update();
        }
    }

    private void handleInput() {

        LogicCommand command = null;

        LogicSnake logicSnake = gameServerHandler.getPlayerSnake();
        LogicGame logicGame = gameServerHandler.getGameState();

        int snakeId = logicSnake.getId();
        int tick = logicGame.getTick();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            command = LogicCommandFactory.getCommand(snakeId, tick, LogicCommand.TURN_LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            command = LogicCommandFactory.getCommand(snakeId, tick, LogicCommand.TURN_RIGHT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            command = LogicCommandFactory.getCommand(snakeId, tick, LogicCommand.TURN_UP);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            command = LogicCommandFactory.getCommand(snakeId, tick, LogicCommand.TURN_DOWN);
        } else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new MainMenuScreen(game));
            return;
        }

        if (command != null) {
            gameServerHandler.registerCommand(command);
        }
    }

    private void updateGraphics() {
        LogicGame logicGame = gameServerHandler.getGameState();
        for (GameObject gameObject : gameObjectList) {
            gameObject.render(logicGame, batch);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // updating the game, rendering and locking the FPS
        beginTime = System.currentTimeMillis();
        readInputAndUpdateGraphics();
        update();
        timeDiff = System.currentTimeMillis() - beginTime;
        sleepTime = (int) (FRAME_PERIOD_IN_MILLIS - timeDiff);
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }

        // catches up in case it got behind the FPS
        int framesSkipped = 0;
        while (sleepTime < 0 && framesSkipped < MAX_FRAMES_SKIPPED) {
            update();
            sleepTime += TIME_TO_SLEEP;
            framesSkipped++;
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
        batch.dispose();
        for (GameObject gameObject : gameObjectList) {
            gameObject.dispose();
        }
    }

}
