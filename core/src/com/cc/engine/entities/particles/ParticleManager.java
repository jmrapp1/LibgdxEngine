package com.cc.engine.entities.particles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParticleManager {

	private static final ParticleManager instance = new ParticleManager();
	private ArrayList<ParticleEffect> effects = new ArrayList<ParticleEffect>();
	
	private ParticleManager() {
	}
	
	public void update() {
		for (int i = 0; i < effects.size(); i++) {
			ParticleEffect effect = effects.get(i);
			if (effect.shouldDispose()) {
				effects.remove(effect);
			} else {
				effect.update();
			}
		}
	}
	
	public void render(SpriteBatch sb) {
		for (ParticleEffect effect : effects)
			effect.render(sb);
	}
	
	public void dipose() {
		for (ParticleEffect effect : effects)
			effect.dispose();
	}
	
	public void addEffect(ParticleEffect effect) {
		effects.add(effect);
	}
	
	public static ParticleManager getInstance() {
		return instance;
	}
	
}
