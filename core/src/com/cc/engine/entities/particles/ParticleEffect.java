package com.cc.engine.entities.particles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class ParticleEffect {

	protected ArrayList<ParticleSystem> systems;
	protected AbstractParticlePool pool;
	protected boolean shouldDispose;
	protected World world;
	protected Vector2 position;
	
	public ParticleEffect(AbstractParticlePool pool, World world, Vector2 position) {
		this.pool = pool;
		this.world = world;
		this.position = position;
	}
	
	public void update() {
		if (world != null && !world.isLocked()) {
			for (int i = 0; i < systems.size(); i++) {
				ParticleSystem sys = systems.get(i);
				if (sys.shouldDispose) {
					systems.remove(sys);
					if (systems.size() == 0) {
						shouldDispose = true;
					}
				} else {
					sys.update();
				}
			}
		}
	}
	
	public void render(SpriteBatch sb) {
		for (ParticleSystem sys : systems)
			sys.render(sb);
	}
	
	public void dispose() {
		for (ParticleSystem sys : systems)
			sys.dispose();
	}
	
	public boolean shouldDispose() {
		return shouldDispose;
	}
	
}
