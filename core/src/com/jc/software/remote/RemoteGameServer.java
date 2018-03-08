package com.jc.software.remote;

import com.jc.software.logic.commands.LogicCommand;

import java.nio.ByteBuffer;

/**
 * Created by jonataschagas on 30/01/18.
 */
public interface RemoteGameServer {

    /**
     * Registers a reference to the objects interested in listening to updates from the game state from the remote server
     * @param listener
     */
    void registerGameUpdateListener(RemoteGameUpdateListener listener);

    /**
     * Sends the command to the server
     *
     * @param byteBuffer
     * @return
     */
    boolean sendCommand(ByteBuffer byteBuffer);

    /**
     * Notifies the game update listener, whenever there is a game update
     */
    void notifyGameUpdateListenerOfUpdate();

    /**
     * sends a request to the game server to find a match.
     * @param playerId
     */
    void findMatch(long playerId);

}
