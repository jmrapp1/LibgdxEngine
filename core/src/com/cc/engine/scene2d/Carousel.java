package com.cc.engine.scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

/**
 * Created by cody on 2/16/17.
 */
public class Carousel extends ScrollPane {

    private boolean wasPanDragFling = false;

    private float pageSpacing;

    private Table content;

    private int currentPageIndex = 0;

    public Carousel() {
        super(null);
        create();
    }

    public Carousel(Skin skin) {
        super(null, skin);
        create();
    }

    public Carousel(Skin skin, String styleName) {
        super(null, skin, styleName);
        create();
    }

    public Carousel(Actor widget, ScrollPaneStyle style) {
        super(null, style);
        create();
    }

    private void create() {
        content = new Table();
        content.defaults().space(50);
        super.setWidget(content);
    }

    public void addPages(Actor... pages) {
        for (Actor page : pages) {
            content.add(page).expandY().fillY();
        }
    }

    public void addPage(Actor page) {
        content.add(page).expandY().fillY();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
            wasPanDragFling = false;
            scrollToPage();
        } else {
            if (isPanning() || isDragging() || isFlinging()) {
                wasPanDragFling = true;
            }
        }
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        if (content != null) {
            for (Cell cell : content.getCells()) {
                cell.width(width);
            }
            content.invalidate();
        }
    }

    public void setPageSpacing(float pageSpacing) {
        this.pageSpacing = pageSpacing;
        if (content != null) {
            content.defaults().space(pageSpacing);
            for (Cell cell : content.getCells()) {
                cell.space(pageSpacing);
            }
            content.invalidate();
        }
    }

    public float getPageSpacing() {
        return pageSpacing;
    }

    private void scrollToPage() {
        final float width = getWidth();
        final float scrollX = getScrollX();
        final float maxX = getMaxX();

        if (scrollX >= maxX || scrollX <= 0) return;

        Array<Actor> pages = getPages();
        float pageX = 0;
        float pageWidth = 0;
        if (pages.size > 0) {
            for (int i = 0; i < pages.size; i ++)
            {
                Actor a = pages.get(i);
                pageX = a.getX();
                pageWidth = a.getWidth();
                if (scrollX < (pageX + pageWidth * 0.5)) {
                    currentPageIndex = i;
                    break;
                }
            }
            setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2, 0, maxX));
        }
    }

    public Array<Actor> getPages () {
        return content.getChildren();
    }

    public int getCurrentPageIndex () {
        return currentPageIndex;
    }

    public void setCurrentPageIndex (int pageIndex) {
        Array<Actor> pages = getPages();
        final float scrollX = getScrollX();
        Actor page = pages.get(pageIndex);
        float pageX = page.getX();
        float pageWidth = getWidth();
        float carouselWidth = getWidth();
        float maxX = getMaxX();

        setScrollX(MathUtils.clamp(pageX - (carouselWidth - pageWidth) / 2, 0, maxX));
    }

    @Override
    public void draw(Batch batch, float alpha)
    {
        super.draw(batch, alpha);
    }

}