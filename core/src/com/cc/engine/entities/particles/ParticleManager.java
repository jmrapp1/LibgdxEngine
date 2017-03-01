package com.cc.engine.entities.particles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class ParticleManager {

	private static final ParticleManager instance = new ParticleManager();
	private ArrayList<ParticleEffect> effects = new ArrayList<ParticleEffect>();

	private ParticleManager() {
	}
	
	public void update(World world) {
		for (int i = 0; i < effects.size(); i++) {
			ParticleEffect effect = effects.get(i);
			if (effect.shouldDispose()) {
				effects.remove(effect);
			} else {
				effect.update(world);
			}
		}
	}
	
	public void render(SpriteBatch sb) {
		for (ParticleEffect effect : effects)
			effect.render(sb);
	}
	
	public void dipose(World world) {
		for (ParticleEffect effect : effects)
			effect.dispose(world);
	}
	
	public void addEffect(ParticleEffect effect) {
		effects.add(effect);
	}
	
	public static ParticleManager getInstance() {
		return instance;
	}
	
}
