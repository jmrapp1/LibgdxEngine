package com.cc.engine.views.drawables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TextureDrawable implements Drawable {

	private final Texture texture;
	private TextureRegion textureRegion;
	
	public TextureDrawable(Texture texture) {
		this.texture = texture;
	}
	
	@Override
	public void update() {	
	}

	@Override
	public void render(SpriteBatch sb, Vector2 pos) {
		sb.draw(texture, pos.x, pos.y);
	}

	@Override
	public void render(SpriteBatch sb, float x, float y) {
		sb.draw(texture, x, y);
	}

	@Override
	public void dispose() {
		texture.dispose();
	}

	public Texture getTexture() {
		return texture;
	}

	@Override
	public TextureRegion getTextureRegion() {
		if (textureRegion == null) {
			textureRegion = new TextureRegion(texture);
		}
		return textureRegion;
	}

	@Override
	public Drawable getInstance() {
		return new TextureDrawable(texture);
	}

	@Override
	public float getWidth() {
		return texture.getWidth();
	}

	@Override
	public float getHeight() {
		return texture.getHeight();
	}

	@Override
	public Object getRaw() {
		return texture;
	}

}
