package com.jc.software.logic.commands;

/**
 * Created by jonataschagas on 26/01/18.
 */
public class LogicIncreaseSnakeLengthCommand implements LogicCommand {

    private int id;

    public LogicIncreaseSnakeLengthCommand(int targetId) {
        this.id = targetId;
    }

    @Override
    public int getType() {
        return LogicCommand.INCREASE_SNAKE_LENGTH;
    }

    @Override
    public int getTargetId() {
        return id;
    }
}
