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

package de.bitbrain.craft.core.professions;

import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Recipe;

/**
 * Abstract implementation of {@see ProfessionLogic}
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
abstract class AbstractProfessionLogic implements ProfessionLogic {

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.core.professions.ProfessionLogic#add(de.bitbrain.craft.models.Item)
	 */
	@Override
	public boolean add(Item item) {
		System.out.println("Dropped item " + item.getId());
		return true;
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.core.professions.ProfessionLogic#setRecipe(de.bitbrain.craft.models.Recipe)
	 */
	@Override
	public void setRecipe(Recipe recipe) {
	}
}