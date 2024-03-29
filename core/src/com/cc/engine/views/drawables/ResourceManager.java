package com.cc.engine.views.drawables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResourceManager {

	private static ResourceManager instance = new ResourceManager();
	private final HashMap<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();
	private final HashMap<String, Drawable> drawables = new HashMap<String, Drawable>();
	private final HashMap<String, Skin> skins = new HashMap<String, Skin>();

	private ResourceManager() {
	}


	public SpriteDrawable loadSpriteDrawable(String id, String file) {
		SpriteDrawable spriteDrawable = new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal(file))));
		spriteDrawable.getSprite().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Nearest);
		drawables.put(id, spriteDrawable);
		return spriteDrawable;
	}

	public void loadTexturedDrawable(String id, String file) {
		Texture t = new Texture(Gdx.files.internal(file));
		t.setFilter(TextureFilter.Linear, TextureFilter.Nearest);
		drawables.put(id, new TextureDrawable(t));
	}

	public void loadAtlasRegionDrawable(String id, TextureAtlas atlas, String region) {
		TextureAtlas.AtlasRegion atlasRegion = atlas.findRegion(region);
		drawables.put(id, new AtlasRegionDrawable(atlasRegion));
	}

	public SpriteDrawable loadAndResizeSpriteDrawable(String id, String loc, float sx, float sy) {
		SpriteDrawable sprite = loadSpriteDrawable(id, loc);
		sprite.getSprite().setScale(sx, sy);
		sprite.getSprite().setOrigin(0, 0);
		return sprite;
	}

	public SpriteDrawable loadAndSetSizeSpriteDrawable(String id, String loc, float width, float height) {
		SpriteDrawable sprite = loadSpriteDrawable(id, loc);
		float sx = width / sprite.getWidth();
		float sy = height / sprite.getHeight();
		sprite.getSprite().setScale(sx, sy);
		sprite.getSprite().setOrigin(0, 0);
		return sprite;
	}

	public Skin loadSkin(String id, String path) {
		Skin skin = new Skin(Gdx.files.internal(path));
		skins.put(id, skin);
		return skin;
	}

	public BitmapFont loadBitmapFont(String id, String fileFNT, String filePNG, Color color, float scale) {
		BitmapFont font = new BitmapFont(Gdx.files.internal(fileFNT),Gdx.files.internal(filePNG),false);
		font.setColor(color);
		return font;
	}

	public BitmapFont loadTrueTypeFont(String id, String file, Color color, int size) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(file));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = size;
		parameter.color = color;
		parameter.minFilter = TextureFilter.Linear;
		parameter.magFilter = TextureFilter.Linear;
		BitmapFont font = gen.generateFont(parameter);
		fonts.put(id, font);
		return font;
	}

	public Skin loadSkinWithDefaultFont(String id, String skinAtlasPath, String skinJsonPath, BitmapFont font) {
		Skin skin = new Skin();
		skin.addRegions(new TextureAtlas(Gdx.files.internal(skinAtlasPath)));
		skin.add("default-font", font, BitmapFont.class);
		skin.load(Gdx.files.internal(skinJsonPath));
		skins.put(id, skin);
		return skin;
	}

	public BitmapFont loadFreeTypeFont(String id, String file, FreeTypeFontGenerator.FreeTypeFontParameter params) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(file));
		BitmapFont font = gen.generateFont(params);
		fonts.put(id, font);
		return font;
	}

	public BitmapFont getFont(String id) {
		return fonts.get(id);
	}

	public Skin getSkin(String id) {
		return skins.get(id);
	}

	public TextureDrawable getTextureDrawable(String id) {
		Drawable drawable = drawables.get(id);
		if (drawable != null)
			return (TextureDrawable) drawable;
		return null;
	}

	public SpriteDrawable getSpriteDrawable(String id) {
		Drawable drawable = drawables.get(id);
		if (drawable != null)
			return (SpriteDrawable) drawable;
		return null;
	}

	public Drawable getDrawable(String id) {
		return drawables.get(id);
	}
	
	public void dispose() {
		Iterator it = drawables.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        ((Drawable)pair.getValue()).dispose();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    it = fonts.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        ((BitmapFont)pair.getValue()).dispose();
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	public static ResourceManager getInstance() {
		return instance;
	}
	
}
