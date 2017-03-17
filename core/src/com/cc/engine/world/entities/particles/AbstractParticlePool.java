package com.cc.engine.world.entities.particles;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public abstract class AbstractParticlePool {

	private Array<AbstractParticle> pool = new Array<AbstractParticle>();
	
	public AbstractParticlePool() {
	}
	
	public AbstractParticle getParticle(World world) {
		if (pool.size > 0) {
			AbstractParticle p = pool.get(pool.size - 1);
			if (p.getBody() == null) {
				pool.removeIndex(pool.size - 1);
				return p;
			}
		}
		return getNewParticle(world);
	}

	protected abstract AbstractParticle getNewParticle(World world);

	public void putParticle(AbstractParticle particle) {
		pool.add(particle);
	}
	
}
