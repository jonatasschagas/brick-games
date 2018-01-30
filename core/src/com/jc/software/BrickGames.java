package com.jc.software;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jc.software.gameobjects.GameObject;
import com.jc.software.gameobjects.Snake;
import com.jc.software.gameobjects.SnakePiece;
import com.jc.software.gameobjects.ui.GameOverLabel;
import com.jc.software.gameobjects.ui.ScoreLabel;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.commands.controls.*;
import com.jc.software.logic.objects.LogicGame;

import java.util.ArrayList;
import java.util.List;

public class BrickGames extends ApplicationAdapter {

    private SpriteBatch batch;
    private LogicGame logicGame;
    private List<GameObject> gameObjectList;

    private void createGameObjects() {
        // Game Elements
        gameObjectList.add(new Snake(logicGame));
        gameObjectList.add(new SnakePiece(logicGame));

        // UI
        gameObjectList.add(new ScoreLabel(logicGame));
        gameObjectList.add(new GameOverLabel(logicGame));
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        logicGame = new LogicGame();
        gameObjectList = new ArrayList<GameObject>();
        createGameObjects();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        update(Gdx.graphics.getDeltaTime());
        handleInput();
        updateGraphics();
        batch.end();
    }

    private void update(float deltaTime) {
        logicGame.update(deltaTime);
    }

    private void handleInput() {
        LogicCommand command = null;
        int snakeId = logicGame.getLogicSnake().getId();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            command = new LogicTurnLeftCommand(snakeId);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            command = new LogicTurnRightCommand(snakeId);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            command = new LogicTurnUpCommand(snakeId);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            command = new LogicTurnDownCommand(snakeId);
        } else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            command = new LogicRestartGameCommand(logicGame.getId());
        }
        if (command != null) {
            logicGame.executeCommand(command);
        }
    }

    private void updateGraphics() {
        for (GameObject gameObject : gameObjectList) {
            gameObject.render(batch);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (GameObject gameObject : gameObjectList) {
            gameObject.dispose();
        }
    }
}
