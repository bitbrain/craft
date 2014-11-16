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

package de.bitbrain.craft.ui.elements;

import de.bitbrain.craft.core.IconManager.IconDrawable;
import de.bitbrain.craft.models.Item.Rarity;

/**
 * UI representation of game data
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface ElementData {
	
	/**
	 * ID of the element
	 * 
	 * @return element id
	 */
	String getId();
	
	/**
	 * Icon of the element
	 * 
	 * @return element icon
	 */
	IconDrawable getIcon();
	
	/**
	 * Description of the element
	 * 
	 * @return element description
	 */
	String getDescription();
	
	/**
	 * Rarity of the element
	 * 
	 * @return element rarity
	 */
	Rarity getRarity();
	
	/**
	 * Name of the element
	 * 
	 * @return element name
	 */
	String getName();
	
	/**
	 * Amount of the element
	 * 
	 * @return element amount
	 */
	int getAmount();
	
	/**
	 * amount of the data
	 * 
	 * @param amount
	 */
	void setAmount(int amount);
	
	/**
	 * Level of the element
	 * 
	 * @return
	 */
	int getLevel();
	
	/**
	 * Creates a copy of this data
	 * 
	 * @return
	 */
	ElementData copy();
}
