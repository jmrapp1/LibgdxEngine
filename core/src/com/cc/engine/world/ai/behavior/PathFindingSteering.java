package com.cc.engine.world.ai.behavior;

import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.math.Vector2;
import com.cc.engine.world.ai.TiledGraph;
import com.cc.engine.world.ai.TiledGraphNode;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/14/2017
 * Time: 9:53 PM
 */
public class PathFindingSteering extends BlendedSteering<Vector2> {

    private PathFindingBehavior pathfindingBehavior;

    public PathFindingSteering(Steerable<Vector2> owner, Steerable<Vector2> targetEntity, TiledGraph graph, IndexedAStarPathFinder<TiledGraphNode> pathfinder) {
        super(owner);

        pathfindingBehavior = new PathFindingBehavior(owner, targetEntity, graph, pathfinder);
        add(pathfindingBehavior, 1);
    }

    public PathFindingBehavior getPathfindingBehavior() {
        return pathfindingBehavior;
    }

    @Override
    protected SteeringAcceleration<Vector2> calculateRealSteering(SteeringAcceleration<Vector2> blendedSteering) {
        pathfindingBehavior.calculatePathNodeReached();
        return super.calculateRealSteering(blendedSteering);
    }

}
