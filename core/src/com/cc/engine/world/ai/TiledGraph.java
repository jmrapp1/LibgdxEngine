package com.cc.engine.world.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/14/2017
 * Time: 6:59 PM
 */
public class TiledGraph implements IndexedGraph<TiledGraphNode> {

    private Array<TiledGraphNode> nodes;

    public TiledGraph(Array<TiledGraphNode> nodes) {
        this.nodes = nodes;
    }

    public TiledGraphNode getNode(int index) {
        return nodes.get(index);
    }

    @Override
    public int getIndex(TiledGraphNode node) {
        return node.getIndex();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public Array<Connection<TiledGraphNode>> getConnections(TiledGraphNode fromNode) {
        return fromNode.getConnections();
    }

}
