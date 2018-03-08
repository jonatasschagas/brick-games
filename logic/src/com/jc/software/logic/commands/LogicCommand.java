package com.jc.software.logic.commands;

import java.nio.ByteBuffer;

/**
 * Created by jonataschagas on 25/01/18.
 */
public interface LogicCommand {

    int getType();

    int getTargetId();

    int getTick();

    void setTick(int tick);

    int TURN_LEFT = 0;
    int TURN_RIGHT = 1;
    int TURN_UP = 2;
    int TURN_DOWN = 3;

}
