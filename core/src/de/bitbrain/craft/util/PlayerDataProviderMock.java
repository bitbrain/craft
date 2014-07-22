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
 * 
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class PlayerDataProviderMock implements PlayerDataProvider {

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.util.PlayerDataProvider#getLevel(de.bitbrain.craft.models.Profession)
	 */
	@Override
	public int getLevel(Profession profession) {
		switch (profession) {
			case ALCHEMIST:
				return 2;
			case ENGINEER:
				return 3;
			case JEWELER:
				return 4;
		}
		
		return 1;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.util.PlayerDataProvider#getProgress(de.bitbrain.craft.models.Profession)
	 */
	@Override
	public float getProgress(Profession profession) {
		switch (profession) {
			case ALCHEMIST:
				return 0.7f;
			case ENGINEER:
				return 0.36f;
			case JEWELER:
				return 0.52f;
		}
		
		return 0.1f;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.util.PlayerDataProvider#getCurrentPlayer()
	 */
	@Override
	public Player getCurrentPlayer() {
		return new Player();
	}

}
