package com.jc.software.remote;

import com.jc.software.CommandBuffer;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.commands.LogicCommandFactory;
import com.jc.software.logic.objects.LogicGame;
import com.jc.software.logic.objects.LogicSnake;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jonataschagas on 18/02/18.
 */
public class GameServerHandler implements RemoteGameUpdateListener {

    private LogicGame gameState;
    private long playerId;
    private RemoteGameServer gameServer;
    private CommandBuffer commandBuffer;
    private AtomicBoolean receivingUpdate;
    private ByteBuffer commandsByteBuffer;

    public GameServerHandler(long playerId, RemoteGameServer gameServer) {
        this.playerId = playerId;
        this.gameServer = gameServer;
        this.commandsByteBuffer = ByteBuffer.allocate(512);
        initialize();
    }

    private void initialize() {
        commandBuffer = new CommandBuffer();
        receivingUpdate = new AtomicBoolean(false);
        gameServer.registerGameUpdateListener(this);
        gameServer.findMatch(playerId);
    }

    @Override
    public void onGameStateUpdated(ByteBuffer byteBuffer) {
        receivingUpdate.set(true);
        if(gameState == null) {
            gameState = new LogicGame();
        }
        // update to state from the server
        gameState.decode(byteBuffer);
        receivingUpdate.set(false);
    }

    @Override
    public void onReceiveCommand(ByteBuffer byteBuffer) {
        int nCommands = byteBuffer.getInt();
        for (int i = 0; i < nCommands; i++) {
            LogicCommand command = LogicCommandFactory.decode(byteBuffer);
            if (command != null) {
                commandBuffer.storeCommand(command.getTick(), command);
            }
        }
    }

    public LogicGame getGameState() {
        return gameState;
    }

    public boolean hasGameStarted() {
        return gameState != null && gameState.getGameStatus() == LogicGame.GameStatus.RUNNING;
    }

    public void update() {
        if (gameState.getGameStatus() == LogicGame.GameStatus.RUNNING && !receivingUpdate.get()) {
            flushCommandsToServer();
            List<LogicCommand> commandsFromTick = commandBuffer.getCommandsFromTick(gameState.getTick());
            if (commandsFromTick != null) {
                for (LogicCommand command : commandsFromTick) {
                    gameState.executeCommand(command);
                }
            }
            gameState.update();
        }
    }

    public LogicSnake getPlayerSnake() {
        return gameState.getSnakeByPlayerId(playerId);
    }

    public void registerCommand(LogicCommand command) {
        if (command != null) {
            // sends the command to the server
            commandBuffer.addCommand(command);
        }
    }

    private void flushCommandsToServer() {
        List<LogicCommand> commands = commandBuffer.flushCommands();
        if (commands != null) {
            commandsByteBuffer.clear();
            commandsByteBuffer.putInt(commands.size());
            for (LogicCommand command : commands) {
                LogicCommandFactory.encode(command, commandsByteBuffer);
            }
            commandsByteBuffer.flip();
            gameServer.sendCommand(commandsByteBuffer);
        }
    }


}
