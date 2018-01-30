package com.jc.software.logic.commands.controls;

import com.jc.software.logic.commands.LogicCommand;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicTurnDownCommand implements LogicCommand {

    private int targetId;

    public LogicTurnDownCommand(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public int getType() {
        return LogicCommand.TURN_DOWN;
    }

    @Override
    public int getTargetId() {
        return targetId;
    }
}
