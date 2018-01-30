package com.jc.software.logic.commands.controls;

import com.jc.software.logic.commands.LogicCommand;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicTurnUpCommand implements LogicCommand {

    private int targetId;

    public LogicTurnUpCommand(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public int getType() {
        return LogicCommand.TURN_UP;
    }

    @Override
    public int getTargetId() {
        return targetId;
    }
}
