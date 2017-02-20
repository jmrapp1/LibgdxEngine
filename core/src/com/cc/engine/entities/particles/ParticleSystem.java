package com.cc.engine.entities.particles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.cc.engine.utils.Timer;

public class ParticleSystem {

	protected int maxParticleCount;
	protected ParticleShapeType shapeType;
	protected Vector2 lifeTimeBounds;
	protected Vector2 minVelocityBounds;
	protected Vector2 maxVelocityBounds;
	protected Vector2 angleBounds;
	protected Vector2 minSpread;
	protected Vector2 maxSpread;
	protected Vector2 rotationVelBounds;
	protected Vector2 scaleBounds;
	protected Vector2 alphaBounds;
	protected Vector2 position;
	protected Vector2 minGravityResBounds;
	protected Vector2 maxGravityResBounds;
	protected Vector2 densityBounds;
	protected Vector2 restitutionBounds;
	protected Vector2 delayBetweenSpawn;
	protected Color startColor;
	protected Color endColor;
	protected float alphaDecay;
	protected float friction;
	protected boolean checkPhysics;
	protected Sprite[] sprites;
	protected boolean constantSystem;
	protected boolean collideWithWorld;
	
	protected AbstractParticlePool pool;
	protected ArrayList<AbstractParticle> particles;
	protected World world;
	
	protected float lastSpawn;
	protected boolean spawnedAll;
	protected boolean shouldDispose;
	
	public ParticleSystem(AbstractParticlePool pool, World world, Sprite[] sprites, int maxParticleCount, ParticleShapeType shapeType, Vector2 position, Vector2 lifeTimeBounds,
						  Vector2 minVelocityBounds, Vector2 maxVelocityBounds, Vector2 angleBounds, Vector2 minSpread, Vector2 maxSpread,
						  Vector2 rotationVelBounds, Vector2 scaleBounds, Vector2 alphaBounds, Vector2 minGravityResBounds,
						  Vector2 maxGravityResBounds, Vector2 densityBounds, Vector2 restitutionBounds, Color startColor, Color endColor, Vector2 delayBetweenSpawn, float alphaDecay,
						  float friction, boolean checkPhysics, boolean constantSystem, boolean collideWithWorld) {
		particles = new ArrayList<AbstractParticle>(maxParticleCount);
		this.pool = pool;
		this.world = world;
		this.sprites = sprites;
		this.maxParticleCount = maxParticleCount;
		this.shapeType = shapeType;
		this.position = position;
		this.lifeTimeBounds = lifeTimeBounds;
		this.minVelocityBounds = minVelocityBounds;
		this.maxVelocityBounds = maxVelocityBounds;
		this.angleBounds = angleBounds;
		this.minSpread = minSpread;
		this.maxSpread = maxSpread;
		this.rotationVelBounds = rotationVelBounds;
		this.scaleBounds = scaleBounds;
		this.alphaBounds = alphaBounds;
		this.minGravityResBounds = minGravityResBounds;
		this.maxGravityResBounds = maxGravityResBounds;
		this.restitutionBounds = restitutionBounds;
		this.densityBounds = densityBounds;
		this.startColor = startColor;
		this.endColor = endColor;
		this.delayBetweenSpawn = delayBetweenSpawn;
		this.alphaDecay = alphaDecay;
		this.friction = friction;
		this.checkPhysics = checkPhysics;
		this.collideWithWorld = collideWithWorld;
		this.constantSystem = constantSystem;
		if (delayBetweenSpawn.equals(Vector2.Zero)) {
			for (int i = 0; i < maxParticleCount; i++) {
				createParticle();
			}
			spawnedAll = true;
		}
	}
	
	public void update() {
		if (!delayBetweenSpawn.equals(Vector2.Zero)) {
			if ((particles.size() < maxParticleCount && !spawnedAll) || constantSystem) {
				float delay = MathUtils.random(delayBetweenSpawn.x, delayBetweenSpawn.x);
				if (Timer.getGameTimeElapsed() - lastSpawn >= delay && !world.isLocked()) {
					createParticle();
					lastSpawn = Timer.getGameTimeElapsed();
				}
			} else {
				if (!spawnedAll && !constantSystem) {
					spawnedAll = true;
				}
			}
		}
		for (int i = 0; i < particles.size(); i++) {
			AbstractParticle p = particles.get(i);
			if (!p.isActive()) {
				p.dispose(pool, world);
				particles.remove(p);
				if (particles.size() == 0 && !constantSystem) {
					shouldDispose = true;
				}
			} else {
				p.update();
			}
		}
	}
	
	public void render(SpriteBatch sb) {
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).render(sb);
	}
	
	public void dispose() {
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).dispose(pool, world);
	}
	
	protected void createParticle() {
		AbstractParticle particle = pool.getParticle();
		Vector2 pos = position;
		Sprite sprite = sprites[MathUtils.random(0, sprites.length - 1)];
		if (minSpread != null && maxSpread != null) {
			pos = position.add(MathUtils.random(minSpread.x, maxSpread.x), MathUtils.random(minSpread.y, maxSpread.y));
		}
		if (scaleBounds != null) {
			sprite = new Sprite(sprite);
			float rand = MathUtils.random(scaleBounds.x, scaleBounds.y);
			sprite.setScale(rand, rand);
		}
		particle.create(shapeType, pos, calculateDirection(MathUtils.random(angleBounds.x, angleBounds.y)), minGravityResBounds != null && maxGravityResBounds != null ? new Vector2(MathUtils.random(minGravityResBounds.x, maxGravityResBounds.x), MathUtils.random(minGravityResBounds.y, maxGravityResBounds.y)) : null, new Vector2(MathUtils.random(minVelocityBounds.x, maxVelocityBounds.x), MathUtils.random(minVelocityBounds.y, maxVelocityBounds.y)), new Sprite(sprite), startColor, endColor, MathUtils.random(lifeTimeBounds.x, lifeTimeBounds.y), friction, alphaBounds != null ? MathUtils.random(alphaBounds.x, alphaBounds.y) : 1, alphaDecay, rotationVelBounds != null ? MathUtils.random(rotationVelBounds.x, rotationVelBounds.y) : 0, MathUtils.random(densityBounds.x, densityBounds.y), MathUtils.random(restitutionBounds.x, restitutionBounds.y), true, checkPhysics, collideWithWorld);
		particles.add(particle);
	}

	public void setToDispose() {
		shouldDispose = true;
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).setToDispose();
	}

	private Vector2 calculateDirection(float angle) {
		float rads = angle * (MathUtils.PI / 180f);
		return new Vector2(MathUtils.cos(rads), MathUtils.sin(rads)).nor();
	}
	
}
