package com.jc.software.logic.commands;

/**
 * Created by jonataschagas on 25/01/18.
 */
public interface LogicCommand {

    int getType();

    int getTargetId();

    int TURN_LEFT = 0;
    int TURN_RIGHT = 1;
    int TURN_UP = 2;
    int TURN_DOWN = 3;

    int UPDATE_SNAKE_PIECE = 4;
    int INCREASE_SNAKE_LENGTH = 5;

}
