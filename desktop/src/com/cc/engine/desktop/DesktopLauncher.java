package com.cc.engine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cc.engine.EngineMain;
import com.cc.engine.utils.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		EngineMain.instantiateSettings();
		config.width = Settings.getWidth();
		config.height = Settings.getHeight();
		new LwjglApplication(new EngineMain(), config);
	}
}
