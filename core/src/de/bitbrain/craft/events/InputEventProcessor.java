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

package de.bitbrain.craft.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.inject.Inject;

import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.inject.SharedInjector;

/**
 * Handler for controls
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class InputEventProcessor extends Stage {
	
	@Inject
	private EventBus eventBus;

	public InputEventProcessor(Viewport viewport, Batch batch) {
		super(viewport, batch);
		SharedInjector.get().injectMembers(this);
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputAdapter#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		super.mouseMoved(screenX, screenY);
		eventBus.fireEvent(new MouseEvent<InputEventProcessor>(EventType.MOUSEMOVE, this, screenX, screenY));	
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputAdapter#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		super.touchDragged(screenX, screenY, pointer);
		eventBus.fireEvent(new MouseEvent<InputEventProcessor>(EventType.MOUSEDRAG, this, screenX, screenY));		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputAdapter#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		super.touchDown(screenX, screenY, pointer, button);
		eventBus.fireEvent(new MouseEvent<InputEventProcessor>(EventType.MOUSEDOWN, this, screenX, screenY));		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputAdapter#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		super.touchUp(screenX, screenY, pointer, button);
		eventBus.fireEvent(new MouseEvent<InputEventProcessor>(EventType.MOUSEUP, this, screenX, screenY));		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keyCode) {
		super.keyDown(keyCode);
		eventBus.fireEvent(new KeyEvent(EventType.KEYDOWN, Gdx.input, keyCode));	
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keyCode) {
		super.keyUp(keyCode);
		eventBus.fireEvent(new KeyEvent(EventType.KEYUP, Gdx.input, keyCode));		
		return true;
	}
}