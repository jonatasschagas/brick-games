package com.jc.software.logic.commands.controls;

import com.jc.software.logic.commands.LogicBaseCommand;
import com.jc.software.logic.commands.LogicCommand;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class LogicTurnUpCommand extends LogicBaseCommand {

    public LogicTurnUpCommand(int targetId, int tick) {
        this.tick = tick;
        this.targetId = targetId;
        this.type = LogicCommand.TURN_UP;
    }
}
