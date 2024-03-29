package com.cc.engine.utils;

import com.badlogic.gdx.Gdx;

public class Timer {

	/** The total time elapsed since the first call to the update() method. */
	private static float timeElapsed;

	/** The total time elapsed since the game timer was started. */
	private static float gameTime;

	/** The time that the game timer was started. */
	private static boolean startGameTime;

	/** Used to "slow" or "speed up" time */
	private static float timeMultiplier = 1;

	/** Private constructor; prevents construction outside the class (singleton design pattern) */
	private Timer() {
	}

	/**
	 * Updates the overall elapsed time and the game time.
	 */
	public static void update() {
		timeElapsed += Gdx.graphics.getDeltaTime() * timeMultiplier;
		if (startGameTime) {
			gameTime += Gdx.graphics.getDeltaTime() * timeMultiplier;
		}
	}

	/**
	 * @return The overall elapsed time
	 */
	public static float getTimeElapsed() {
		return timeElapsed;
	}

	/**
	 * @return The game time elapsed since the timer's start
	 */
	public static float getGameTimeElapsed() {
		return gameTime;
	}

	/**
	 * Start the game timer.
	 */
	public static void startGameTime() {
		startGameTime = true;
	}

	/**
	 * Stop the game timer.
	 */
	public static void stopGameTime() {
		startGameTime = false;
	}

	public static void setTimeMultiplier(float multiplier) {
		timeMultiplier = multiplier;
	}

	/**
	 *  Resets and turns off the game timer.
	 */
	public static void resetGameTime() {
		gameTime = 0;
		startGameTime = false;
	}
	
}
