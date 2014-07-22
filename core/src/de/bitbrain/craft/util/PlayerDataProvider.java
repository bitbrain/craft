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

package de.bitbrain.craft.util;

import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.models.Profession;

/**
 * Provides data of the current player
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface PlayerDataProvider {

	/**
	 * Provides the level of profession 
	 * 
	 * @param profession target profession
	 * @return current profession level
	 */
	int getLevel(Profession profession);
	
	/**
	 * Returns the percentage of progress
	 * 
	 * @param profession target profession
	 * @return current profession level
	 */
	float getProgress(Profession profession);
	
	/**
	 * Returns the current player
	 * 
	 * @return current player of the game
	 */
	Player getCurrentPlayer();
}
