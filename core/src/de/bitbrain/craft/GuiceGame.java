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

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.inject.StateScope;

/**
 * Injected implementation of a game
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public abstract class GuiceGame extends Game {

	private Screen current;

	@Inject
	@Named("stateScope")
	StateScope scope;
	
	public GuiceGame() {
		current = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.Game#setScreen(com.badlogic.gdx.Screen)
	 */
	public final void setScreen(Class<? extends Screen> screenClass) {		
		if (current != null) {
			scope.leave();
		}
		scope.enter(screenClass);
		Screen screen = SharedInjector.get().getInstance(screenClass);
		super.setScreen(screen);
		current = screen;
	}
	
	@Override
	public void setScreen(Screen screen) {
		setScreen(screen.getClass());
	}
}
