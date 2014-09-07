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

import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.core.IconManager;
import de.bitbrain.craft.core.IconManager.Icon;
import de.bitbrain.craft.models.Item.Rarity;
import de.bitbrain.craft.models.Recipe;
import de.bitbrain.craft.ui.ElementInfo.ElementData;

/**
 * Adapter for recipes as elements
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class RecipeElementAdapter implements ElementData {
	
	private Recipe recipe;
	
	private Icon icon;
	
	public RecipeElementAdapter(Recipe recipe) {
		this.recipe = recipe;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getIcon()
	 */
	@Override
	public Icon getIcon() {
		if (icon == null) {
			icon = IconManager.getInstance().fetch(recipe.getIcon());
		}
		
		return icon;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getDescription()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getNameColor()
	 */
	@Override
	public Rarity getRarity() {
		return Rarity.COMMON;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getName()
	 */
	@Override
	public String getName() {
		return Bundles.recipes.get(recipe.getId());
	}
}