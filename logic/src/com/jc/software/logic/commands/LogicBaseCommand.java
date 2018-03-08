package com.jc.software.logic.commands;

import java.nio.ByteBuffer;

/**
 * Created by jonataschagas on 28/02/18.
 */
public abstract class LogicBaseCommand implements LogicCommand {

    protected int tick;
    protected int type;
    protected int targetId;

    public void setTick(int tick) {
        this.tick = tick;
    }

    public int getTick() {
        return this.tick;
    }

    @Override
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

}
