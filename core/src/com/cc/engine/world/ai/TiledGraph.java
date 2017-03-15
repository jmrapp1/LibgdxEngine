package com.cc.engine.world.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.cc.engine.world.ai.heuristic.ManhattanDistance;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/14/2017
 * Time: 6:59 PM
 */
public class TiledGraph implements IndexedGraph<TiledGraphNode> {

    private static float PIXELS_TO_METERS = 100;

    private Array<TiledGraphNode> nodes;
    private int width, height;
    private float nodePixelSize;

    public TiledGraph(Array<TiledGraphNode> nodes, int width, int height, float nodePixelSize) {
        this.nodes = nodes;
        this.width = width;
        this.height = height;
        this.nodePixelSize = nodePixelSize;
    }

    public GraphPath<TiledGraphNode> findPath(IndexedAStarPathFinder<TiledGraphNode> pathfinder, Vector2 startPos, Vector2 goalPos) {
        GraphPath<TiledGraphNode> path = new DefaultGraphPath<TiledGraphNode>();
        int iSX = (int)(startPos.x * PIXELS_TO_METERS / nodePixelSize); //Multiply by 100 to get to pixel coordinates
        int iSY = (int)(startPos.y * PIXELS_TO_METERS / nodePixelSize);
        int iGX = (int)(goalPos.x * PIXELS_TO_METERS / nodePixelSize);
        int iGY = (int)(goalPos.y * PIXELS_TO_METERS / nodePixelSize);
        int startIndex = (iSY * height) + iSX;
        int goalIndex = (iGY * height) + iGX;
        boolean resultFound = pathfinder.searchNodePath(getNode(startIndex), getNode(goalIndex), new ManhattanDistance(), path);
        if (resultFound)
            return path;
        return null;
    }

    public float getNodePixelSize() {
        return nodePixelSize;
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
