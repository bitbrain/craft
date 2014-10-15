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

package de.bitbrain.craft;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

import de.bitbrain.craft.inject.StateScope;

/**
 * Injected implementation of a game
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public abstract class GuiceGame extends Game {

	private Map<Integer, Screen> states;

	private Screen current;

	@Inject
	@Named("stateScope")
	StateScope scope;
	
	public GuiceGame() {
		states = new HashMap<Integer, Screen>();
		current = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Game#setScreen(com.badlogic.gdx.Screen)
	 */
	@Override
	public final void setScreen(Screen screen) {
		
		if (current != null) {
			scope.leave();
		}
		
		current = screen;
		
		if (current != null) {
			scope.enter(screen.getClass());
			super.setScreen(screen);
		}
	}
	
	public final void setScreen(int id) {
		setScreen(states.get(id));
	}
}
