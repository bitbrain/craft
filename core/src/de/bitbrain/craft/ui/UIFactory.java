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

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import de.bitbrain.craft.Styles;

/**
 * Factory which creates UI components
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class UIFactory {
	
	/**
	 * Creates a green primary button
	 * 
	 * @param caption text of the button
	 * @return new button instance
	 */
	public static TextButton createPrimaryButton(String caption) {
		TextButton button = new TextButton(caption, Styles.BTN_GREEN);
		return button;
	}

}
