package com.jc.software;

import com.jc.software.logic.commands.LogicCommand;

import java.util.*;

/**
 * Created by jonataschagas on 06/02/18.
 */
public class CommandBuffer {

    private Queue<LogicCommand> buffer = new LinkedList<LogicCommand>();
    private Map<Integer, List<LogicCommand>> commandsByTick = new HashMap<Integer, List<LogicCommand>>();
    private LogicCommand lastCommand;

    public void addCommand(LogicCommand command) {
        // don't send to server duplicate commands
        if (lastCommand != null && command.getType() == lastCommand.getType()) {
            return;
        }
        buffer.add(command);
        lastCommand = command;
    }

    public List<LogicCommand> flushCommands() {
        List<LogicCommand> commandList = null;
        if (!buffer.isEmpty()) {
            commandList = new ArrayList<LogicCommand>();
            while (!buffer.isEmpty()) {
                commandList.add(buffer.poll());
            }
        }
        return commandList;
    }

    public List<LogicCommand> getCommandsFromTick(int tick) {
        return commandsByTick.get(tick);
    }

    public void storeCommand(int tick, LogicCommand logicCommand) {
        List<LogicCommand> listOfCommandsFromTick = commandsByTick.get(tick);
        if (listOfCommandsFromTick == null) {
            listOfCommandsFromTick = new ArrayList<LogicCommand>();
        }
        listOfCommandsFromTick.add(logicCommand);
        commandsByTick.put(tick, listOfCommandsFromTick);
    }

}
