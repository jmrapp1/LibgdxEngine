package com.cc.engine.world.ai;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/15/2017
 * Time: 3:11 PM
 */
public interface INodeCoordinateConverter {

    float nodeCoordinateToWorld(int nodeCoordinate);

    int nodeCoordinateFromWorld(float worldCoordinate);

}
