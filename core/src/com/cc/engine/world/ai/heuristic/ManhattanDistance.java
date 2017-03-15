package com.cc.engine.world.ai.heuristic;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.cc.engine.world.ai.TiledGraphNode;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/14/2017
 * Time: 7:04 PM
 */
public class ManhattanDistance implements Heuristic<TiledGraphNode> {

    @Override
    public float estimate (final TiledGraphNode node, final TiledGraphNode endNode) {
        return Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY());
    }

}

