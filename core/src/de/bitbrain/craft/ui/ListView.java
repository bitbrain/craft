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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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

	public ListView() {
		items = new ArrayList<Actor>();
	}
	
	public void addActor(Actor actor) {
		items.add(actor);
		actor.addCaptureListener(new Mover());
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
		
		float lastY = 25f;
		setWidth(getParent().getWidth());
		setHeight(getParent().getHeight() + 50f);
		
		for (Actor item : items) {

			if (item.getY() < 0f || getY() + item.getY() + item.getHeight() > getY() + getHeight()) {
				break;
			} else {
				item.getColor().g = 100f;
			}
			
			float oldHeight = item.getHeight();
			item.setBounds(getX() + padding, getY() + lastY + padding, getWidth() - padding * 2f, item.getHeight() - padding * 2f);
			item.getColor().a = getColor().a;
			
			item.draw(batch, parentAlpha);
			item.setHeight(oldHeight);
			lastY += item.getHeight() + spacing + padding;
		}
	}
	
	private static class Mover extends ClickListener {
		
		/* (non-Javadoc)
		 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
		 */
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
			System.out.println("Clicked!");
		}
	}
}
