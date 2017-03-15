package com.cc.engine.world.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.cc.engine.world.ai.converter.INodeCoordinateConverter;
import com.cc.engine.world.ai.heuristic.ManhattanDistance;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/14/2017
 * Time: 6:59 PM
 */
public class TiledGraph implements IndexedGraph<TiledGraphNode> {

    private Array<TiledGraphNode> nodes;
    private com.cc.engine.world.ai.converter.INodeCoordinateConverter nodeCoordinateConverter;

    private int width, height;

    public TiledGraph(Array<TiledGraphNode> nodes, int width, int height, INodeCoordinateConverter nodeCoordinateConverter) {
        this.nodes = nodes;
        this.width = width;
        this.height = height;
        this.nodeCoordinateConverter = nodeCoordinateConverter;
    }

    public GraphPath<TiledGraphNode> findPath(IndexedAStarPathFinder<TiledGraphNode> pathfinder, Vector2 startPos, Vector2 goalPos) {
        return findPath(pathfinder, new ManhattanDistance(), startPos, goalPos);
    }

    public GraphPath<TiledGraphNode> findPath(IndexedAStarPathFinder<TiledGraphNode> pathfinder, Heuristic<TiledGraphNode> searchHeuristic, Vector2 startPos, Vector2 goalPos) {
        GraphPath<TiledGraphNode> path = new DefaultGraphPath<TiledGraphNode>();
        int iSX = nodeCoordinateConverter.nodeCoordinateFromWorld(startPos.x);
        int iSY = nodeCoordinateConverter.nodeCoordinateFromWorld(startPos.y);
        int iGX = nodeCoordinateConverter.nodeCoordinateFromWorld(goalPos.x);
        int iGY = nodeCoordinateConverter.nodeCoordinateFromWorld(goalPos.y);
        int startIndex = (iSY * height) + iSX;
        int goalIndex = (iGY * height) + iGX;
        boolean resultFound = pathfinder.searchNodePath(getNode(startIndex), getNode(goalIndex), searchHeuristic, path);
        if (resultFound)
            return path;
        return null;
    }

    public INodeCoordinateConverter getNodeCoordinateConverter() {
        return nodeCoordinateConverter;
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
