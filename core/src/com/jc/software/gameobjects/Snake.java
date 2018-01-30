package com.jc.software.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jc.software.logic.objects.LogicGame;
import com.jc.software.logic.objects.LogicSnake;
import com.jc.software.logic.objects.LogicSnakeSegment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class Snake implements GameObject {

    private List<SnakeNode> snakeNodeList;
    private LogicGame logicGame;

    public Snake(LogicGame logicGame) {
        snakeNodeList = new ArrayList<SnakeNode>();
        this.logicGame = logicGame;
        LogicSnake logicSnake = logicGame.getLogicSnake();
        snakeNodeList.add(new SnakeNode(logicSnake.getX(), logicSnake.getY()));
    }

    @Override
    public void render(SpriteBatch batch) {
        LogicSnake logicSnake = logicGame.getLogicSnake();
        int i = 0;
        for (LogicSnakeSegment snakeSegment : logicSnake.getSnakeSegmentList()) {
            renderSnakeSegment(snakeSegment, i, batch);
            i++;
        }
    }

    private void renderSnakeSegment(LogicSnakeSegment segment, int nodeIndex, SpriteBatch batch) {

        if (nodeIndex >= snakeNodeList.size()) {
            snakeNodeList.add(new SnakeNode(segment.getX(), segment.getY()));
        }

        // only one node now.
        SnakeNode snakeNode = snakeNodeList.get(nodeIndex);

        // update the cordinates
        snakeNode.setX(segment.getX());
        snakeNode.setY(segment.getY());

        snakeNode.render(batch);
    }

    @Override
    public void dispose() {
        for (SnakeNode snakeNode : snakeNodeList) {
            snakeNode.dispose();
        }
    }

}
