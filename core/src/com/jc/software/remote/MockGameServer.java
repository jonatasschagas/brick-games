package com.jc.software.remote;

import com.jc.software.CommandBuffer;
import com.jc.software.GameConfiguration;
import com.jc.software.logic.commands.LogicCommand;
import com.jc.software.logic.commands.LogicCommandFactory;
import com.jc.software.logic.objects.LogicGame;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.jc.software.GameConfiguration.FPS;

/**
 * Created by jonataschagas on 30/01/18.
 */
public class MockGameServer implements RemoteGameServer, Runnable {

    private static MockGameServer instance;
    private List<RemoteGameUpdateListener> listeners;
    private LogicGame gameState;
    private ConcurrentLinkedQueue<LogicCommand> commandBlockingQueue;
    private CommandBuffer commandBuffer;
    private Thread serverThread;
    private ByteBuffer gameUpdateByteBuffer;
    private ByteBuffer commandsByteBuffer;

    private MockGameServer() {
        serverThread = new Thread(this);
        serverThread.setName(MockGameServer.class.getSimpleName());
        commandBlockingQueue = new ConcurrentLinkedQueue<LogicCommand>();
        serverThread.start();
        listeners = new ArrayList<RemoteGameUpdateListener>();
        commandBuffer = new CommandBuffer();
        gameUpdateByteBuffer = ByteBuffer.allocate(2024);
        commandsByteBuffer = ByteBuffer.allocate(128);
    }

    public static MockGameServer getInstance() {
        if (instance == null) {
            instance = new MockGameServer();
        }
        return instance;
    }

    @Override
    public void registerGameUpdateListener(RemoteGameUpdateListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public boolean sendCommand(ByteBuffer byteBuffer) {
        int nCommands = byteBuffer.getInt();
        for(int i = 0; i < nCommands; i++) {
            LogicCommand logicCommand = LogicCommandFactory.decode(byteBuffer);
            // setting the tick to the future
            int originalTick = logicCommand.getTick();
            // TODO:: set the forwarding of the tick based on the avg latency between clients and server
            logicCommand.setTick((int) (originalTick + GameConfiguration.SNAKE_VELOCITY));
            commandBlockingQueue.add(logicCommand);
            // broadcast command to the clients
            for (RemoteGameUpdateListener listener : listeners) {
                commandsByteBuffer.clear();
                commandsByteBuffer.putInt(1);
                LogicCommandFactory.encode(logicCommand, commandsByteBuffer);
                commandsByteBuffer.flip();
                listener.onReceiveCommand(commandsByteBuffer);
            }
        }
        return true;
    }

    @Override
    public void notifyGameUpdateListenerOfUpdate() {
        for (RemoteGameUpdateListener listener : listeners) {
            gameUpdateByteBuffer.clear();
            gameState.encode(gameUpdateByteBuffer);
            gameUpdateByteBuffer.flip();
            listener.onGameStateUpdated(gameUpdateByteBuffer);
        }
    }

    @Override
    public void findMatch(long playerId) {
        if (gameState == null) {
            gameState = new LogicGame();
        }
        gameState.startGame(playerId);
        if (gameState.getGameStatus() == LogicGame.GameStatus.RUNNING) {
            notifyGameUpdateListenerOfUpdate();
        }
    }


    @Override
    public void run() {

        while (true) {

            if (gameState == null) {
                sleep(100);
                continue;
            }

            // adding commands to the buffer
            while (!commandBlockingQueue.isEmpty()) {
                LogicCommand command = commandBlockingQueue.poll();
                commandBuffer.storeCommand(command.getTick(), command);
            }

            // calculating how many ticks to advance in the game
            long deltaMs = System.currentTimeMillis() - gameState.getStartGameMillis();
            int ticksToNow = (int) ((deltaMs * FPS) / 1000);
            int ticksToAdvance = ticksToNow - gameState.getTick();

            // advances the game state and executes commands
            for (int i = 0; i < ticksToAdvance; i++) {
                List<LogicCommand> commandsFromTick = commandBuffer.getCommandsFromTick(gameState.getTick());
                if (commandsFromTick != null) {
                    for (LogicCommand command : commandsFromTick) {
                        gameState.executeCommand(command);
                    }
                }
                gameState.update();
            }

            // updates the listeners with the latest state of the game
            notifyGameUpdateListenerOfUpdate();

            // sleeps
            sleep(1000);
        }
    }

    private void sleep(int timeInMillis) {
        try {
            // create a fake delay of max 50 ms
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
