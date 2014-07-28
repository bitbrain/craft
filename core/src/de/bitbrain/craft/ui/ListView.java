/*
 * Craft - Crafting game for Android, PC and Browser.
 * Copyright (C) 2014 Miguel Gonzalez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package de.bitbrain.craft.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

/**
 * A list view which provides vertical data presentation with scrolling
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ListView extends Actor {

	private List<Actor> items;
	
	private float padding, spacing;
	
	private ScrollHandler scrollHandler;
	
	private Rectangle scissors = new Rectangle(); 
	private Rectangle clipBounds = new Rectangle();

	public ListView() {
		scrollHandler = new ScrollHandler();
		items = new ArrayList<Actor>();
		
		addCaptureListener(scrollHandler);
	}
	
	public void addActor(Actor actor) {
		items.add(actor);
	}
	
	public void setPadding(float padding) {
		this.padding = padding;
	}
	
	public void setSpacing(float spacing) {
		this.spacing = spacing;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
		super.act(delta);
		for (Actor item : items) {
			item.act(delta);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		clipBounds.x = getX();
		clipBounds.y = getY();
		clipBounds.width = getWidth();
		clipBounds.height = getHeight() - padding * 3;
		
		float lastY = scrollHandler.getOffset();
		setWidth(getParent().getWidth());
		setHeight(getParent().getHeight());
		
		ScissorStack.calculateScissors(getStage().getCamera(), batch.getTransformMatrix(), clipBounds, scissors); 
		ScissorStack.pushScissors(scissors); 
		
		final float alphaThreshold = 60f;
		
		
		for (Actor item : items) {
			
			float oldHeight = item.getHeight();
			item.setBounds(getX() + padding, getY() + lastY + padding, getWidth() - padding * 2f, item.getHeight() - padding * 2f);
			item.getColor().a = getColor().a;
			
			if (lastY < alphaThreshold) {
				item.getColor().a = 1f - (alphaThreshold - lastY) / alphaThreshold;
			}
			
			if (lastY > getHeight() - alphaThreshold - padding * 3) {
				item.getColor().a = (alphaThreshold - getHeight() - lastY) / alphaThreshold;
			}
			
			item.draw(batch, parentAlpha);
			item.setHeight(oldHeight);
			lastY += item.getHeight() + spacing + padding;
		}
		batch.flush();
		ScissorStack.popScissors();
	}
	
	
	private static class ScrollHandler extends InputListener {
		
		private float offset;
		
		private float clickOffset;
		
		/* (non-Javadoc)
		 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDragged(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int)
		 */
		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer) {
			super.touchDragged(event, x, y, pointer);
			offset = y - clickOffset;
		}
		
		
		/* (non-Javadoc)
		 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
		 */
		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			clickOffset = y;
			return true;
		}
		
		public float getOffset() {
			return offset;
		}
	}
}
