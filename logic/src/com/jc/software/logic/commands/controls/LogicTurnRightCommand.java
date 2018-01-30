package com.jc.software.logic.commands.controls;

import com.jc.software.logic.commands.LogicCommand;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicTurnRightCommand implements LogicCommand {

    private int targetId;

    public LogicTurnRightCommand(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public int getType() {
        return LogicCommand.TURN_RIGHT;
    }

    @Override
    public int getTargetId() {
        return targetId;
    }
}
