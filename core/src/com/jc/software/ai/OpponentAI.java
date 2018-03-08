package com.jc.software.ai;

import com.jc.software.logic.commands.controls.LogicTurnRightCommand;
import com.jc.software.logic.commands.controls.LogicTurnUpCommand;
import com.jc.software.logic.objects.LogicGame;
import com.jc.software.logic.objects.LogicSnake;
import com.jc.software.logic.objects.LogicSnakePiece;
import com.jc.software.remote.GameServerHandler;
import com.jc.software.remote.MockGameServer;

import java.util.Random;

import static com.jc.software.GameConfiguration.FRAME_PERIOD;
import static com.jc.software.GameConfiguration.MAX_FRAMES_SKIPPED;

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

    public void update() {
        if (gameServerHandler.hasGameStarted()) {

            LogicGame game = gameServerHandler.getGameState();
            LogicSnake snake = game.getSnakeByPlayerId(playerId);
            LogicSnakePiece snakePiece = game.getLogicSnakePiece();

            if ((snake.getBufferedDirection() == LogicSnake.Direction.UP || snake.getBufferedDirection() == LogicSnake.Direction.DOWN) &&
                    snakePiece.getTileY() == snake.getTileY()) {
                gameServerHandler.registerCommand(new LogicTurnRightCommand(snake.getId(), game.getTick()));
            } else if (snake.getBufferedDirection() == LogicSnake.Direction.RIGHT && snakePiece.getTileY() != snake.getTileY()) {
                gameServerHandler.registerCommand(new LogicTurnUpCommand(snake.getId(), game.getTick()));
            }

            gameServerHandler.update();
        }
    }

    @Override
    public void run() {
        while (gameServerHandler.hasGameStarted()) {
            // updating the game, rendering and locking the FPS
            beginTime = System.currentTimeMillis();
            update();
            timeDiff = System.currentTimeMillis() - beginTime;
            sleepTime = (int) (FRAME_PERIOD - timeDiff);
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
                sleepTime += FRAME_PERIOD;
                framesSkipped++;
            }
        }
    }
}
