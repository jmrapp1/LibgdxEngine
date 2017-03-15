package com.cc.engine.world.entities.particles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class ParticleEffect {

	protected ArrayList<ParticleSystem> systems;
	protected AbstractParticlePool pool;
	protected boolean shouldDispose;
	protected Vector2 position;
	
	public ParticleEffect(AbstractParticlePool pool, Vector2 position) {
		this.pool = pool;
		this.position = position;
	}
	
	public void update(World world, float cameraX, float cameraY) {
		if (world != null && !world.isLocked()) {
			for (int i = 0; i < systems.size(); i++) {
				ParticleSystem sys = systems.get(i);
				if (sys.shouldDispose) {
					systems.remove(sys);
					if (systems.size() == 0) {
						shouldDispose = true;
					}
				} else {
					sys.update(world, cameraX, cameraY);
				}
			}
		}
	}
	
	public void render(SpriteBatch sb, float cameraX, float cameraY) {
		for (ParticleSystem sys : systems)
			sys.render(sb, cameraX, cameraY);
	}
	
	public void dispose(World world) {
		if (!shouldDispose)
			setToDispose();
		for (ParticleSystem sys : systems)
			sys.dispose(world);
	}
	
	public boolean shouldDispose() {
		return shouldDispose;
	}

	public void setToDispose() {
		shouldDispose = true;
		for (ParticleSystem sys : systems)
			sys.setToDispose();
	}
	
}
