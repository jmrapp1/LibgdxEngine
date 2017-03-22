package com.cc.engine.views.scene2d;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cc.engine.input.InputController;
import com.cc.engine.utils.Settings;

/**
 * Created by cody on 2/15/17.
 */
public abstract class BaseDialog extends Dialog {

    private float vPadding;
    private float hPadding;

    public BaseDialog(String title, Skin skin) {
        this(title, skin, 35, 35);
    }

    public BaseDialog(String title, Skin skin, float hPadding, float vPadding) {
        super(title, skin);
        this.hPadding = hPadding;
        this.vPadding = vPadding;
    }

    @Override
    public Dialog show(Stage stage) {
        super.show(stage);
        InputController.getInstance().setDialogProcessor(stage);
        return this;
    }

    @Override
    public void hide(Action action) {
        super.hide(action);
        InputController.getInstance().removeDialogProcessor();
    }

    public abstract void onBackPressed();

    @Override
    public float getPrefWidth() {
        return Settings.getWidth() - hPadding;
    }

    @Override
    public float getPrefHeight() {
        return Settings.getHeight() - vPadding;
    }
}
