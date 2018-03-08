package com.jc.software.logic.objects;

import com.jc.software.GameConfiguration;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.util.Utils;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicGame {

    private LogicSnake logicSnake1;
    private LogicSnake logicSnake2;
    private LogicSnakePiece logicSnakePiece;
    private int score1;
    private int score2;
    private GameStatus gameStatus;
    private long player1Id;
    private long player2Id;
    private long startGameMillis;
    private int tickFromStart;
    private long[] snakePiecePositions;
    private int currentSnakePiecePositionIndex;

    public LogicGame() {
        score1 = 0;
        score2 = 0;
        player1Id = 0;
        player2Id = 0;
        gameStatus = GameStatus.LOADING;
        startGameMillis = 0;
        tickFromStart = 0;
    }

    public void executeCommand(LogicCommand command) {
        LogicGameObject[] gameObjects = new LogicGameObject[]{logicSnake1, logicSnake2, logicSnakePiece};
        for (LogicGameObject gameObject : gameObjects) {
            // only passes the commands to the right target objects
            if (command.getTargetId() == gameObject.getId() && command.getTick() == getTick()) {
                gameObject.executeCommand(command);
            }
        }
    }

    public void update() {

        if (gameStatus == GameStatus.LOADING || gameStatus == GameStatus.GAME_OVER) {
            return;
        }

        tickFromStart++;

        logicSnake1.update(tickFromStart);
        logicSnake2.update(tickFromStart);
        logicSnakePiece.update(tickFromStart);

        // check collision with snake piece
        checkSnakeHitTarget(logicSnake1);
        checkSnakeHitTarget(logicSnake2);

        // check if the segments of the snake collided
        checkSnakeHitAnotherSnakeSegment(logicSnake1, logicSnake1.getSnakeSegmentList());
        checkSnakeHitAnotherSnakeSegment(logicSnake1, logicSnake2.getSnakeSegmentList());

        checkSnakeHitAnotherSnakeSegment(logicSnake2, logicSnake1.getSnakeSegmentList());
        checkSnakeHitAnotherSnakeSegment(logicSnake2, logicSnake2.getSnakeSegmentList());

    }

    private void checkSnakeHitTarget(LogicSnake logicSnake) {
        LogicSnakeSegment head = logicSnake.getHead();
        if (collide(head.getTileX(), head.getTileY(), logicSnakePiece.getTileX(), logicSnakePiece.getTileY())) {
            logicSnake.increaseSnakeLength();
            // selecting next location for snake piece
            currentSnakePiecePositionIndex++;
            // generating new snake pieces
            if (currentSnakePiecePositionIndex == snakePiecePositions.length) {
                currentSnakePiecePositionIndex = 0;
                generateRandomCoordinatesForSnakePiece();
            }
            logicSnakePiece.updateSnakePiece(getXFromSnakePiece(), getYFromSnakePiece());
            if (logicSnake.getPlayerId() == player1Id) {
                score1++;
            } else {
                score2++;
            }
        }
    }

    private void checkSnakeHitAnotherSnakeSegment(LogicSnake logicSnake, List<LogicSnakeSegment> segmentList) {
        LogicSnakeSegment head = logicSnake.getHead();
        for (LogicSnakeSegment s : segmentList) {
            if (head.getId() != s.getId() && collide(head.getTileX(), head.getTileY(), s.getTileX(), s.getTileY())) {
                gameStatus = GameStatus.GAME_OVER;
                return;
            }
        }
    }

    public void startGame(long playerId) {

        if (player1Id == 0) {
            player1Id = playerId;
        } else if (player2Id == 0) {
            player2Id = playerId;
        }

        // if we have both player ids, we start the game
        if (player1Id != 0 && player2Id != 0) {
            startGameMillis = System.currentTimeMillis();
            gameStatus = GameStatus.RUNNING;
            logicSnake1 = new LogicSnake(1, player1Id, 1);
            logicSnake2 = new LogicSnake(2, player2Id, 2);
            generateRandomCoordinatesForSnakePiece();
            currentSnakePiecePositionIndex = 0;
            logicSnakePiece = new LogicSnakePiece();
            int x = getXFromSnakePiece();
            int y = getYFromSnakePiece();
            logicSnakePiece.updateSnakePiece(x, y);
        }

    }

    private void generateRandomCoordinatesForSnakePiece() {
        if (snakePiecePositions == null) {
            snakePiecePositions = new long[20];
        }
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(GameConfiguration.SCREEN_WIDTH_IN_TILES);
            int y = random.nextInt(GameConfiguration.SCREEN_HEIGHT_IN_TILES);
            snakePiecePositions[i] = Utils.asLong(x, y);
        }
    }

    private int getXFromSnakePiece() {
        long location = snakePiecePositions[currentSnakePiecePositionIndex];
        return Utils.getX(location);
    }

    private int getYFromSnakePiece() {
        long location = snakePiecePositions[currentSnakePiecePositionIndex];
        return Utils.getY(location);
    }

    private boolean collide(int xa, int ya, int xb, int yb) {
        return xa == xb && ya == yb;
    }

    public LogicSnakePiece getLogicSnakePiece() {
        return logicSnakePiece;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getTick() {
        return tickFromStart;
    }

    public long getPlayer1Id() {
        return player1Id;
    }

    public long getPlayer2Id() {
        return player2Id;
    }

    public LogicSnake getSnakeByPlayerId(long playerId) {
        if (playerId == player1Id) {
            return logicSnake1;
        } else {
            return logicSnake2;
        }
    }

    public int getScoreByPlayerId(long playerId) {
        if (playerId == player1Id) {
            return score1;
        } else {
            return score2;
        }
    }

    public int getPlayerNumber(long playerId) {
        if (playerId == player1Id) {
            return 1;
        } else {
            return 2;
        }
    }

    public long getStartGameMillis() {
        return startGameMillis;
    }

    public void encode(ByteBuffer byteBuffer) {
        byteBuffer.putInt(score1);
        byteBuffer.putInt(score2);
        byteBuffer.putInt(gameStatus.ordinal());
        byteBuffer.putLong(player1Id);
        byteBuffer.putLong(player2Id);
        byteBuffer.putLong(startGameMillis);
        byteBuffer.putInt(tickFromStart);
        byteBuffer.putInt(currentSnakePiecePositionIndex);
        int snakePiecePositionsSize = snakePiecePositions != null ? snakePiecePositions.length : 0;
        byteBuffer.putInt(snakePiecePositionsSize);
        for (int i = 0; i < snakePiecePositionsSize; i++) {
            byteBuffer.putLong(snakePiecePositions[i]);
        }
        logicSnake1.encode(byteBuffer);
        logicSnake2.encode(byteBuffer);
        logicSnakePiece.encode(byteBuffer);
    }

    public void decode(ByteBuffer byteBuffer) {
        score1 = byteBuffer.getInt();
        score2 = byteBuffer.getInt();
        gameStatus = GameStatus.values()[byteBuffer.getInt()];
        player1Id = byteBuffer.getLong();
        player2Id = byteBuffer.getLong();
        startGameMillis = byteBuffer.getLong();
        tickFromStart = byteBuffer.getInt();
        currentSnakePiecePositionIndex = byteBuffer.getInt();
        int snakePiecePositionsSize = byteBuffer.getInt();
        if (snakePiecePositionsSize > 0 && snakePiecePositions == null) {
            snakePiecePositions = new long[20];
        }
        for (int i = 0; i < snakePiecePositionsSize; i++) {
            snakePiecePositions[i] = byteBuffer.getLong();
        }
        if(logicSnake1 == null) {
            logicSnake1 = new LogicSnake();
        }
        logicSnake1.decode(byteBuffer);
        if(logicSnake2 == null) {
            logicSnake2 = new LogicSnake();
        }
        logicSnake2.decode(byteBuffer);
        if(logicSnakePiece == null) {
            logicSnakePiece = new LogicSnakePiece();
        }
        logicSnakePiece.decode(byteBuffer);
    }

    public enum GameStatus {
        LOADING,
        RUNNING,
        GAME_OVER
    }

}
