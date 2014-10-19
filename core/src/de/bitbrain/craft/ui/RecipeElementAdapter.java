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

import com.google.inject.Inject;

import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.core.IconManager;
import de.bitbrain.craft.core.IconManager.Icon;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item.Rarity;
import de.bitbrain.craft.models.Recipe;

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
	
	@Inject 
	IconManager iconManager;
	
	public RecipeElementAdapter(Recipe recipe) {
		SharedInjector.get().injectMembers(this);
		this.recipe = recipe;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getIcon()
	 */
	@Override
	public Icon getIcon() {
		if (icon == null) {
			icon = iconManager.fetch(recipe.getIcon());
		}
		
		return icon;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Recipe";
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getNameColor()
	 */
	@Override
	public Rarity getRarity() {
		return Rarity.RARE;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getName()
	 */
	@Override
	public String getName() {
		return Bundles.recipes.get(recipe.getId());
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getAmount()
	 */
	@Override
	public int getAmount() {
		return -1;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getId()
	 */
	@Override
	public String getId() {
		return recipe.getId();
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementData#setAmount(int)
	 */
	@Override
	public void setAmount(int amount) {
		// do nothing
	}
}