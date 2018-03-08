package com.jc.software.logic.objects;

import com.jc.software.logic.commands.LogicCommand;

import java.nio.ByteBuffer;

/**
 * Created by jonataschagas on 25/01/18.
 */
public interface LogicGameObject {

    void executeCommand(LogicCommand command);

    void update(int tick);

    int getId();

    int getTileX();

    int getTileY();

    void encode(ByteBuffer byteBuffer);

    void decode(ByteBuffer byteBuffer);

}
