package com.jc.software.remote;

import java.nio.ByteBuffer;

/**
 * The game must implement this interface in order to receive game state updates from the "server"
 */
public interface RemoteGameUpdateListener {

    void onGameStateUpdated(ByteBuffer byteBuffer);

    void onReceiveCommand(ByteBuffer byteBuffer);

}
