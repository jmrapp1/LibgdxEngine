package com.cc.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.cc.engine.camera.OrthoCamera;

import java.security.InvalidParameterException;

public class InputController {

	/** Singleton instance of the class */
	private static InputController instance = new InputController();

	/** Holds all input processors and allows each to take input simultaneously */
	private final InputMultiplexer processors = new InputMultiplexer();

	/** Singleton constructor */
	private InputController() {
		Gdx.input.setInputProcessor(processors); //Set the processors
	}

	/**
	 * Add an input processor to accept input events
	 *
	 * @param ip The processor
	 */
	public void addInputProcessor(InputProcessor ip) {
		processors.addProcessor(ip);
		reattachInputProcessors();
	}

	/**
	 * Remove an input processor
	 *
	 * @param ip The processor
	 */
	public void removeInputProcessor(InputProcessor ip) {
		processors.removeProcessor(ip);
		reattachInputProcessors();
	}

	/** Clear all processors except for the GestureDetector. */
	public void clearProcessors() {
		processors.clear();
		reattachInputProcessors();
	}

	public void reattachInputProcessors () {
		Gdx.input.setInputProcessor(processors);
	}

	/**
	 * Adds the dialog processor (usually a stage)
	 * @param ip
	 */
	public void setDialogProcessor(InputProcessor ip) {
		Gdx.input.setInputProcessor(ip);
	}

	/**
	 * Removes the current dialog processor
	 */
	public void removeDialogProcessor() {
		reattachInputProcessors();
	}

	/**
	 * Singleton method that returns the single instance of the class.
	 *
	 * @return The single class instance
	 */
	public static InputController getInstance() {
		return instance;
	}


}
