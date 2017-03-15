package com.cc.engine.world.ai;

import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 3/14/2017
 * Time: 6:57 PM
 */
public class GraphConverter {

    public static TiledGraph tiledMapToGraph(TiledMapTileLayer colliderLayer, com.cc.engine.world.ai.converter.INodeCoordinateConverter nodeCoordinateConverter) {
        TiledGraphNode[][] nodes = new TiledGraphNode[colliderLayer.getWidth()][colliderLayer.getHeight()];
        Array<TiledGraphNode> indexedNodes = new Array<TiledGraphNode>(colliderLayer.getWidth() * colliderLayer.getHeight());

        int index = 0;
        for (int y = 0; y < colliderLayer.getHeight(); y++) {
            for (int x = 0; x < colliderLayer.getWidth(); x++, index++) {
                nodes[x][y] = new TiledGraphNode(index, x, y, 4);
                indexedNodes.add(nodes[x][y]);
            }
        }

        for (int y = 0; y < colliderLayer.getHeight(); y++) {
            for (int x = 0; x < colliderLayer.getWidth(); x++) {
                if (colliderLayer.getCell(x, y) == null) {
                    if (x - 1 >= 0 && colliderLayer.getCell(x - 1, y) == null) {
                        nodes[x][y].getConnections().add(new DefaultConnection<TiledGraphNode>(nodes[x][y], nodes[x - 1][y]));
                    }

                    if (x + 1 < colliderLayer.getWidth() && colliderLayer.getCell(x + 1, y) == null) {
                        nodes[x][y].getConnections().add(new DefaultConnection<TiledGraphNode>(nodes[x][y], nodes[x + 1][y]));
                    }

                    if (y - 1 >= 0 && colliderLayer.getCell(x, y - 1) == null) {
                        nodes[x][y].getConnections().add(new DefaultConnection<TiledGraphNode>(nodes[x][y], nodes[x][y - 1]));
                    }

                    if (y + 1 < colliderLayer.getHeight() && colliderLayer.getCell(x, y + 1) == null) {
                        nodes[x][y].getConnections().add(new DefaultConnection<TiledGraphNode>(nodes[x][y], nodes[x][y + 1]));
                    }
                }
            }
        }

        return new TiledGraph(indexedNodes, colliderLayer.getWidth(), colliderLayer.getHeight(), nodeCoordinateConverter);
    }

}
