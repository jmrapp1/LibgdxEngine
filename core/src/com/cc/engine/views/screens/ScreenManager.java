package com.cc.engine.views.screens;


import com.cc.engine.utils.Settings;

public class ScreenManager {

	private static Screen current, last;
	
	public static void setScreen(Screen s) {
		if (current != null) {
			current.dispose();
		}
		last = current;
		current = s;
		current.create();
		current.resize(Settings.getWidth(), Settings.getHeight());
	}
	
	public static void goBack() {
		if (current != null) {
			current.dispose();
		}
		Screen temp = last;
		last = current;
		current = temp;
		current.resume();
	}
	
	public static Screen getCurrent() {
		return current;
	}
	
}
