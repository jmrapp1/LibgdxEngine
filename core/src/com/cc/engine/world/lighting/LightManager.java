package com.cc.engine.world.lighting;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import javafx.scene.effect.Light;

/**
 * Created with IntelliJ IDEA.
 * User: Jon
 * Date: 2/20/2017
 * Time: 7:45 PM
 */
public class LightManager {

    private static final LightManager instance = new LightManager();

    private RayHandler rayHandler;

    private LightManager() {
    }

    public void initialize(RayHandler rayHandler) {
        this.rayHandler = rayHandler;
    }

    public void update() {
        rayHandler.update();
    }

    public void render(OrthographicCamera camera) {
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public void dispose() {
        rayHandler.dispose();
    }

    public static LightManager getInstance() {
        return instance;
    }

}
