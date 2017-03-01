package com.cc.engine.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    private ShapeRenderer shapeRenderer;

    private boolean wasPanDragFling = false;

    private boolean drawPageIndicator = true;

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
        shapeRenderer = new ShapeRenderer();
    }

    public void setDrawPageIndicator(boolean drawPageIndicator) {
        this.drawPageIndicator = drawPageIndicator;
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
            for (int i = 0; i < pages.size; i++) {
                Actor a = pages.get(i);
                pageX = a.getX();
                pageWidth = a.getWidth();
                if (scrollX < (pageX + pageWidth * 0.5)) {
                    break;
                }
            }
            setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2, 0, maxX));
        }
    }

    @Override
    public void setScrollX(float pixels) {
        super.setScrollX(pixels);
        final float scrollX = getScrollX();
        float pageX = 0;
        float pageWidth = 0;
        float visibility = 0;
        float lowestVisibility = 9999;
        int visibilityIndex = 0;
        Array<Actor> pages = getPages();
        for (int i = 0; i < pages.size; i++) {
            Actor page = pages.get(i);
            pageX = page.getX();
            pageWidth = page.getWidth();
            visibility = Math.abs((scrollX + pageWidth / 2) - pageX);
            if (visibility <= lowestVisibility) {
                lowestVisibility = visibility;
                visibilityIndex = i;
            }
        }
        currentPageIndex = visibilityIndex;
    }

    public Array<Actor> getPages() {
        return content.getChildren();
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int pageIndex) {
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
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (this.drawPageIndicator) {
            // TODO: Create way to show what the current page is
            Array<Actor> pages = getPages();
            int currentPageIndex = getCurrentPageIndex();
            float x, y = getY() + 15, radius = 10;
            float widthOfAllCircles = (pages.size - 1) * (radius * 2);
            for (int i = 0; i < pages.size; i++) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                if (currentPageIndex == i) {
                    shapeRenderer.setColor(Color.GRAY);
                } else {
                    shapeRenderer.setColor(Color.BLACK);
                }
                x = getX() + (getWidth() / 2) - (widthOfAllCircles / 2);
                x += (i * radius * 2);
                shapeRenderer.circle(x, y, radius);
                shapeRenderer.end();
            }
        }
    }

}