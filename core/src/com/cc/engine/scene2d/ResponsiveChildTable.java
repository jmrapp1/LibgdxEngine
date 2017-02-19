package com.cc.engine.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * A table who's children will all have the same width/height
 * Created by cody on 2/19/17.
 */
public class ResponsiveChildTable extends Table {

    @Override
    public void layout() {
        super.layout();
        int colCount = getColumns();
        float actorWidth = getWidth() / colCount;
        for (Actor actor : getChildren()) {
            actor.setWidth(actorWidth);
            actor.setHeight(actorWidth);
        }
    }
}
