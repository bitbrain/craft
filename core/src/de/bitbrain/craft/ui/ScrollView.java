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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.google.inject.Inject;

import de.bitbrain.craft.inject.SharedInjector;

/**
 * Provides scrolling into vertical and horizontal direction
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ScrollView extends Actor {

	private Actor content;
	
	public ScrollView(Actor content) {
		SharedInjector.get().injectMembers(this);
		if (content != null) {
			this.content = content;
		} else {
			throw new RuntimeException("ScrollView has no content defined (content is null)");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#getWidth()
	 */
	@Override
	public float getWidth() {
		if (getParent() != null) {
			return getParent().getWidth();
		} else {
			return super.getWidth();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#getHeight()
	 */
	@Override
	public float getHeight() {
		if (getParent() != null) {
			return getParent().getHeight();
		} else {
			return super.getHeight();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#getX()
	 */
	@Override
	public float getX() {
		if (getParent() != null) {
			return getParent().getX();
		} else {
			return super.getX();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#getX()
	 */
	@Override
	public float getY() {
		if (getParent() != null) {
			return getParent().getY();
		} else {
			return super.getY();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#hit(float, float, boolean)
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return content.hit(x, y, touchable);
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		content.setPosition(getX(), getY());
		content.setSize(getWidth(), getHeight());
		content.draw(batch, getColor().a * parentAlpha);
	}
}
