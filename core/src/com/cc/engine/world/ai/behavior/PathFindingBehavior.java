package com.cc.engine.world.ai.behavior;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.cc.engine.world.ai.TiledGraph;
import com.cc.engine.world.ai.TiledGraphNode;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/14/2017
 * Time: 8:54 PM
 */
public class PathFindingBehavior extends Arrive<Vector2> {

    /** The graph that contains the borders of the map */
    private TiledGraph graph;
    /** The path finder a* method used to find a path between two nodes */
    private IndexedAStarPathFinder<TiledGraphNode> pathfinder;
    /** The target entity that we are trying to find a path to */
    private Steerable<Vector2> targetEntity;
    /** The found path */
    private GraphPath<TiledGraphNode> path;

    /** The position that the target was at when finding the last path */
    private Vector2 lastTargetPosition;
    /** The target node location that the behavior is targeted at. */
    private NodeLocation targetNodeLoc;
    /** The current index we're at in the path */
    private int pathIndex = 0;
    /** The maximum distance that determines if the owner has arrived at the target node */
    private float nodeArrivedTolerance = .05f;
    /** The minimum distance that determines if the target entity has moved far away enough that we should recalculate the path*/
    private float targetMovedTolerance = 1.5f;

    /**
     * Steering behavior that takes a target entity and constantly calculates and recalculates a path to that entity.
     *
     * @param owner The entity that will be moving towards the target
     * @param targetEntity The target entity to calculate a path and move towards
     * @param graph The graph of the map
     * @param pathfinder The A* path finding method
     */
    public PathFindingBehavior(Steerable<Vector2> owner, Steerable<Vector2> targetEntity, TiledGraph graph, IndexedAStarPathFinder<TiledGraphNode> pathfinder) {
        super(owner);
        this.targetEntity = targetEntity;
        this.graph = graph;
        this.pathfinder = pathfinder;
        targetNodeLoc = new NodeLocation(graph.getNodeCoordinateConverter().nodeCoordinateFromWorld(owner.getPosition().x), graph.getNodeCoordinateConverter().nodeCoordinateFromWorld(owner.getPosition().y)); //Set location to owner in case path isnt found
        this.target = targetNodeLoc; //Set target to this node object
        findPathToTarget(); //Find initial path
    }

    /**
     * Calculates a path from the owner to the target for navigation/
     */
    private void findPathToTarget() {
        GraphPath<TiledGraphNode> tempPath = graph.findPath(pathfinder, owner.getPosition(), targetEntity.getPosition()); //Get path from position to target position
        if (tempPath != null) { //If the path is found
            path = tempPath; //Set the path
            lastTargetPosition = targetEntity.getPosition().cpy(); //Hold this target position for comparison when checking if moved
            pathIndex = 1; //Go back to start of path. Index 0 is usually their position, so go to the 1st index
            TiledGraphNode node = path.get(pathIndex); //Get node
            targetNodeLoc.setNodePosition(node.getX(), node.getY()); //Set position
        }
    }

    /**
     * Determines if the node we're targeting has been reached. If it has the
     * path index will be incremented and the target will be set to the next node
     * if we haven't reached the end of the path yet
     */
    public void calculatePathNodeReached() {
        float dst = owner.getPosition().dst(getTarget().getPosition());
        if (dst <= nodeArrivedTolerance) { //If within certain distance of target
            if (pathIndex + 1 < path.getCount()) { //If we didnt reach the end of the path
                pathIndex++; //Go to next node
                TiledGraphNode node = path.get(pathIndex); //Get next node
                targetNodeLoc.setNodePosition(node.getX(), node.getY()); //Set node in target location
            }
        }
        checkTargetMoved(); //Check if the target moved and we need to recalculate path
    }

    /**
     * Check if the target position has moved outside of the tolerance distance. If
     * it has, recalculate a new path and begin following it.
     */
    private void checkTargetMoved() {
        float dst = targetEntity.getPosition().dst(lastTargetPosition); //Get the distance from the target position to their last position used
        if (dst >= targetMovedTolerance) { //If it's outside the tolerance
            findPathToTarget(); //Recalculate path
        }
    }

    public float getNodeArrivedTolerance() {
        return nodeArrivedTolerance;
    }

    public void setNodeArrivedTolerance(float nodeArrivedTolerance) {
        this.nodeArrivedTolerance = nodeArrivedTolerance;
    }

    public float getTargetMovedTolerance() {
        return targetMovedTolerance;
    }

    public void setTargetMovedTolerance(float targetMovedTolerance) {
        this.targetMovedTolerance = targetMovedTolerance;
    }

    @Override
    public Location<Vector2> getTarget() {
        return targetNodeLoc;
    }

    public TiledGraph getGraph() {
        return graph;
    }

    public void setGraph(TiledGraph graph) {
        this.graph = graph;
    }

    public Steerable<Vector2> getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(Steerable<Vector2> targetEntity) {
        this.targetEntity = targetEntity;
    }

    public GraphPath<TiledGraphNode> getPath() {
        return path;
    }

    public void setPath(GraphPath<TiledGraphNode> path) {
        this.path = path;
    }

    private class NodeLocation implements Location<Vector2> {

        /** The position vector to the node. Holds world coordinates */
        private Vector2 pos = new Vector2();

        /**
         * Class that represents a node location that is being followed. This is used
         * when navigating along a path. It takes node coordinates and then converts
         * them to world coordinates for the owner to follow.
         * @param nodeX The node x position in the graph
         * @param nodeY The node y position in the graph
         */
        public NodeLocation(int nodeX, int nodeY) {
            setNodePosition(nodeX, nodeY);
        }

        /**
         * Takes a node position in the graph and converts it to world coordinates that the
         * owner will then start navigating to.
         * @param nodeX The node x position in the graph
         * @param nodeY The node y position in the graph
         */
        public void setNodePosition(int nodeX, int nodeY) {
            pos.set(graph.getNodeCoordinateConverter().nodeCoordinateToWorld(nodeX), graph.getNodeCoordinateConverter().nodeCoordinateToWorld(nodeY)); //Convert node coordinates (in tiles) to pixels, move it to center of tile, then divide to get world coordinates
        }

        @Override
        public Vector2 getPosition() {
            return pos;
        }

        @Override
        public float getOrientation() {
            return owner.getOrientation();
        }

        @Override
        public void setOrientation(float orientation) {
            owner.setOrientation(orientation);
        }

        @Override
        public float vectorToAngle(Vector2 vector) {
            return owner.vectorToAngle(vector);
        }

        @Override
        public Vector2 angleToVector(Vector2 outVector, float angle) {
            return owner.angleToVector(outVector, angle);
        }

        @Override
        public Location<Vector2> newLocation() {
            return owner.newLocation();
        }
    }

}
