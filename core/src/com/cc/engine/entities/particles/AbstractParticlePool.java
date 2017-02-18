package com.cc.engine.entities.particles;

import java.util.ArrayList;

public abstract class AbstractParticlePool {

	private ArrayList<AbstractParticle> pool = new ArrayList<AbstractParticle>();
	
	public AbstractParticlePool() {
	}
	
	public AbstractParticle getParticle() {
		if (pool.size() > 0) {
			AbstractParticle p = pool.get(pool.size() - 1);
			if (p.getBody() == null)
				return p;
		}
		return getNewParticle();
	}

	protected abstract AbstractParticle getNewParticle();

	public void putParticle(AbstractParticle particle) {
		pool.add(particle);
	}
	
}
