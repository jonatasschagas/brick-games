package com.jc.software.logic.commands;

import com.jc.software.logic.commands.controls.LogicTurnDownCommand;
import com.jc.software.logic.commands.controls.LogicTurnLeftCommand;
import com.jc.software.logic.commands.controls.LogicTurnRightCommand;
import com.jc.software.logic.commands.controls.LogicTurnUpCommand;

import java.nio.ByteBuffer;

/**
 * Created by jonataschagas on 04/03/18.
 */
public class LogicCommandFactory {


    public static LogicCommand getCommand(int targetId, int tick, int type) {
        LogicCommand command = null;
        switch (type) {
            case LogicCommand.TURN_UP:
                command = new LogicTurnUpCommand(targetId, tick);
                break;
            case LogicCommand.TURN_DOWN:
                command = new LogicTurnDownCommand(targetId, tick);
                break;
            case LogicCommand.TURN_LEFT:
                command = new LogicTurnLeftCommand(targetId, tick);
                break;
            case LogicCommand.TURN_RIGHT:
                command = new LogicTurnRightCommand(targetId, tick);
                ;
                break;
        }
        return command;
    }

    public static LogicCommand decode(ByteBuffer byteBuffer) {
        int type = byteBuffer.getInt();
        int targetId = byteBuffer.getInt();
        int tick = byteBuffer.getInt();
        return getCommand(targetId, tick, type);
    }

    public static void encode(LogicCommand command, ByteBuffer byteBuffer) {
        byteBuffer.putInt(command.getType());
        byteBuffer.putInt(command.getTargetId());
        byteBuffer.putInt(command.getTick());
    }

}
