package com.cc.engine.views.screens;


import com.cc.engine.utils.Settings;

public class ScreenManager {

	private static Screen current, last;
	
	public static void setScreen(Screen s, boolean dispose) {
		if (current != null && dispose) {
			current.dispose();
		}
		last = current;
		current = s;
		current.create();
	}

	public static void setScreen(Screen s) {
		setScreen(s, true);
	}

	public static void goBack(boolean resume) {
		if (current != null) {
			current.dispose();
		}
		Screen temp = last;
		last = current;
		current = temp;
		if (resume) {
			current.resume();
		}
	}

	public static void goBack() {
		goBack(true);
	}
	
	public static Screen getCurrent() {
		return current;
	}
	
}
