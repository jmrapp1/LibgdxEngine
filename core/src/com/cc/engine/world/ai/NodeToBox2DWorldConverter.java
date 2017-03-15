package com.cc.engine.world.ai;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/15/2017
 * Time: 3:14 PM
 */
public class NodeToBox2DWorldConverter implements INodeCoordinateConverter {

    private float pixelsToMeter, nodePixelSize;

    public NodeToBox2DWorldConverter(float pixelsToWorld, float tileSize) {
        this.pixelsToMeter = pixelsToWorld;
        this.nodePixelSize = tileSize;
    }

    @Override
    public float nodeCoordinateToWorld(int nodeCoordinate) {
        return ((nodeCoordinate * nodePixelSize) + nodePixelSize / 2) / pixelsToMeter;
    }

    @Override
    public int nodeCoordinateFromWorld(float worldCoordinate) {
        return (int)(worldCoordinate * pixelsToMeter / nodePixelSize); //Multiply by 100 to get to pixel coordinates, then divide to get pixels to node (tile) size
    }

}
