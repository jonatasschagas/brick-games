package com.jc.software.ai;

import com.jc.software.logic.commands.controls.LogicTurnDownCommand;
import com.jc.software.logic.commands.controls.LogicTurnLeftCommand;
import com.jc.software.logic.commands.controls.LogicTurnRightCommand;
import com.jc.software.logic.commands.controls.LogicTurnUpCommand;
import com.jc.software.logic.objects.LogicGame;
import com.jc.software.logic.objects.LogicSnake;
import com.jc.software.logic.objects.LogicSnakePiece;
import com.jc.software.remote.GameServerHandler;
import com.jc.software.remote.MockGameServer;

import java.util.Random;

import static com.jc.software.GameConfiguration.*;

/**
 * Created by jonataschagas on 11/02/18.
 */
public class OpponentAI implements Runnable {

    private static OpponentAI instance;
    private GameServerHandler gameServerHandler;
    private long playerId;
    private long beginTime;
    private long timeDiff;
    private long sleepTime;

    private OpponentAI() {
        initialize();
    }

    public static OpponentAI getOpponentAI() {
        if (instance == null) {
            instance = new OpponentAI();
        }
        return instance;
    }

    private void initialize() {
        Random random = new Random();
        playerId = random.nextInt(10000);
        gameServerHandler = new GameServerHandler(playerId, MockGameServer.getInstance());
    }

    public GameServerHandler getGameServerHandler() {
        return gameServerHandler;
    }

    public void update() {
        if (gameServerHandler.hasGameStarted()) {

            LogicGame game = gameServerHandler.getGameState();
            LogicSnake snake = game.getSnakeByPlayerId(playerId);
            LogicSnakePiece snakePiece = game.getLogicSnakePiece();

            if (snake.getTileY() + 1 == snakePiece.getTileY()) {
                if(snake.getTileX() < snakePiece.getTileX()) {
                    gameServerHandler.registerCommand(new LogicTurnRightCommand(snake.getId(), game.getTick()));
                } else {
                    gameServerHandler.registerCommand(new LogicTurnLeftCommand(snake.getId(), game.getTick()));
                }
            } else if (snake.getTileY() != snakePiece.getTileY()
                    && snake.getTileX() + 1 == snakePiece.getTileX()) {
                if(snake.getTileY() < snakePiece.getTileY()) {
                    gameServerHandler.registerCommand(new LogicTurnUpCommand(snake.getId(), game.getTick()));
                } else {
                    gameServerHandler.registerCommand(new LogicTurnDownCommand(snake.getId(), game.getTick()));
                }
            }

            gameServerHandler.update();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!gameServerHandler.hasGameStarted()) {
                continue;
            }
            // updating the game, rendering and locking the FPS
            beginTime = System.currentTimeMillis();
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
    }
}
