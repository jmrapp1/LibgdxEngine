package com.cc.engine.world.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/14/2017
 * Time: 6:59 PM
 */
public class TiledGraphNode {

    private final int index;
    private final int x;
    private final int y;
    private final Array<Connection<TiledGraphNode>> connections;

    public TiledGraphNode(final int index, final int x, final int y, final int capacity) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.connections = new Array<Connection<TiledGraphNode>>(capacity);
    }

    public int getIndex () {
        return index;
    }

    public Array<Connection<TiledGraphNode>> getConnections () {
        return connections;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString () {
        return "IndexedNodeFake [index=" + index + ", x=" + x + ", y=" + y + ", connections=" + connections + "]";
    }


}
