package com.jc.software.logic.objects;

import com.jc.software.logic.commands.LogicCommand;

/**
 * Created by jonataschagas on 25/01/18.
 */
public interface LogicGameObject {

    void executeCommand(LogicCommand command);

    void update(float deltaTime);

    int getId();

    float getX();

    float getY();

    float getWidth();

    float getHeight();

}
