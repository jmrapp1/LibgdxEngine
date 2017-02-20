package com.cc.engine.entities.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.cc.engine.utils.Timer;

public abstract class AbstractParticle {

	protected static final float PIXELS_TO_METERS = 100f;

	protected World world;

	protected ParticleShapeType shapeType;
	protected Vector2 position;
	protected Vector2 direction;
	protected Vector2 gravityResistance;
	protected Color startColor;
	protected Color endColor;
	protected float lifeTime;
	protected float friction;
	protected float alpha;
	protected float alphaDecay;
	protected float rotationalVel;
	protected float density;
	protected float restitution;
	protected boolean active;
	protected boolean checkPhysics;
	protected short categoryBits;
	protected short maskBits;

	protected Body body;
	protected Sprite sprite;
	protected boolean collideWithWorld;
	protected float lifeStart;


	public AbstractParticle(World world, short categoryBits, short maskBits) {
		this.world = world;
		this.categoryBits = categoryBits;
		this.maskBits = maskBits;
	}

	public void create(ParticleShapeType shapeType, Vector2 position, Vector2 direction, Vector2 gravityResistance, Vector2 velocity, Sprite sprite,
			Color startColor, Color endColor, float lifeTime, float friction, float initAlpha,
			float alphaDecay, float rotationalVel, float density, float restitution, boolean active, boolean checkPhysics, boolean collideWithWorld) {
		this.shapeType = shapeType;
		this.position = position;
		this.direction = direction;
		this.gravityResistance = gravityResistance;
		this.sprite = sprite;
		this.startColor = startColor;
		this.endColor = endColor;
		this.rotationalVel = rotationalVel;
		this.lifeTime = lifeTime;
		this.friction = friction;
		this.alpha = initAlpha;
		this.alphaDecay = alphaDecay;
		this.density = density;
		this.restitution = restitution;
		this.collideWithWorld = collideWithWorld;
		if (checkPhysics) {
			createBody();
			body.applyForceToCenter(velocity.scl(direction), true);
			body.applyTorque(rotationalVel, true);
		}
		this.checkPhysics = checkPhysics;
		this.active = true;
		sprite.setAlpha(initAlpha);
		sprite.setPosition(position.x, position.y);
		lifeStart = Timer.getGameTimeElapsed();
	}

	private void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(position.x / PIXELS_TO_METERS, position.y / PIXELS_TO_METERS);
		body = world.createBody(bodyDef);
		Shape shape = null;
		if (shapeType == ParticleShapeType.CIRCLE) {
			shape = new CircleShape();
			shape.setRadius(sprite.getHeight() * sprite.getScaleY() / 2 / PIXELS_TO_METERS);
		} else if (shapeType == ParticleShapeType.BOX){
			shape = new PolygonShape();
			((PolygonShape)shape).setAsBox(((sprite.getWidth() * sprite.getScaleX()) / 2) / PIXELS_TO_METERS, ((sprite.getHeight() * sprite.getScaleY())/ 2) / PIXELS_TO_METERS);
		}

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;
		fixtureDef.filter.categoryBits = categoryBits;
		fixtureDef.filter.maskBits = maskBits;

		body.createFixture(fixtureDef);

		shape.dispose();
	}

	public void update() {
		if (checkPhysics) {
			if (body != null && isActive()) {
				position.set((body.getPosition().x * PIXELS_TO_METERS) - (sprite.getWidth() * sprite.getScaleX() / 2), (body.getPosition().y * PIXELS_TO_METERS) - (sprite.getHeight() * sprite.getScaleY() / 2));
				sprite.setPosition(position.x, position.y);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				if (alpha > 0) {
					alpha = MathUtils.clamp(alpha - alphaDecay, 0, 1);
					sprite.setAlpha(alpha);
				}
			}
		} else {

		}
		if (Timer.getGameTimeElapsed() - lifeStart > lifeTime) {
			active = false;
		}
	}

	public void render(SpriteBatch sb) {
		sprite.draw(sb);
	}

	public void dispose(AbstractParticlePool pool, World world) {
		active = false;
		removeBody(world);
	}

	public void setToDispose() {
		active = false;
	}

	protected abstract void removeBody(World world);

	public boolean isActive() {
		return active;
	}

	public Body getBody() {
		return body;
	}
	
}
