package com.cc.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cc.engine.audio.AudioManager;
import com.cc.engine.input.InputController;
import com.cc.engine.utils.Settings;
import com.cc.engine.utils.Timer;
import com.cc.engine.views.drawables.ResourceManager;
import com.cc.engine.views.screens.ScreenManager;

public class EngineMain extends ApplicationAdapter {

	protected SpriteBatch sb;
	protected Color clearColor = new Color(0, 0, 0, 1);
	protected boolean paused;
	protected long lastBack;

	@Override
	public void create () {
		sb = new SpriteBatch(150);
		sb.enableBlending();
		instantiateSettings();
		Gdx.input.setCatchBackKey(true); //Stops from exiting when back button pressed
	}

	@Override
	public void render() {
		if (!paused) {
			Timer.update();

			if (ScreenManager.getCurrent() != null)
				ScreenManager.getCurrent().update();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.BACK) && System.currentTimeMillis() - lastBack > 300) {
			if (ScreenManager.getCurrent() != null) {
				ScreenManager.getCurrent().onBackPressed();
			}
			lastBack = System.currentTimeMillis();
		}

		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);

		if (ScreenManager.getCurrent() != null) {
			ScreenManager.getCurrent().render(sb);
		}
	}

	@Override
	public void resize(int width, int height) {
		if (ScreenManager.getCurrent() != null)
			ScreenManager.getCurrent().resize(width, height);
	}

	@Override
	public void pause() {
		paused = true;
		if (ScreenManager.getCurrent() != null)
			ScreenManager.getCurrent().pause();
	}

	@Override
	public void resume() {
		paused = false;
		if (ScreenManager.getCurrent() != null)
			ScreenManager.getCurrent().resume();
	}

	@Override
	public void dispose() {
		sb.dispose();
		ResourceManager.getInstance().dispose();
		InputController.getInstance().clearProcessors();
	}

	public static void instantiateSettings() {
		Settings.addSetting("width", 800);
		Settings.addSetting("height", 480);
	}

}
