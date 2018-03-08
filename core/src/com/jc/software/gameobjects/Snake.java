package com.jc.software.gameobjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jc.software.GameConfiguration;
import com.jc.software.logic.objects.LogicGame;
import com.jc.software.logic.objects.LogicSnake;
import com.jc.software.logic.objects.LogicSnakeSegment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.jc.software.GameConfiguration.TILE_SIZE;

/**
 * Created by jonataschagas on 25/01/18.
 */
public class Snake implements GameObject {

    private List<SnakeNode> snakeNodeList;
    private long playerId;
    private int playerNumber;

    public Snake(long playerId, int playerNumber) {
        this.playerNumber = playerNumber;
        snakeNodeList = new ArrayList<SnakeNode>();
        snakeNodeList.add(new SnakeNode(0, 0, playerNumber));
        this.playerId = playerId;
    }

    @Override
    public void render(LogicGame logicGame, SpriteBatch batch) {
        LogicSnake logicSnake = logicGame.getSnakeByPlayerId(playerId);
        for(int i = 0; i < logicSnake.getSnakeSegmentList().size(); i++) {
            renderSnakeSegment(logicGame, logicSnake.getSnakeSegmentList().get(i), i, batch);
        }
    }

    private void renderSnakeSegment(LogicGame logicGame, LogicSnakeSegment snakeSegment, int nodeIndex, SpriteBatch batch) {

        float x = snakeSegment.getTileX() * TILE_SIZE;
        float y = snakeSegment.getTileY() * TILE_SIZE;

        if (nodeIndex >= snakeNodeList.size()) {
            snakeNodeList.add(new SnakeNode(x, y, playerNumber));
        }

        // only one node now.
        SnakeNode snakeNode = snakeNodeList.get(nodeIndex);

        // update the cordinates
        snakeNode.setX(x);
        snakeNode.setY(y);

        snakeNode.render(logicGame, batch);
    }

    @Override
    public void dispose() {
        for (SnakeNode snakeNode : snakeNodeList) {
            snakeNode.dispose();
        }
    }

}
