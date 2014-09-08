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
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Item.Rarity;
import de.bitbrain.craft.ui.ElementInfo.ElementData;

/**
 * Adapter for items in order to show them as elements
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ItemElementAdapter implements ElementData {
	
	private Item item;
	
	private Icon icon;
	
	private int amount;
	
	public ItemElementAdapter(Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getIcon()
	 */
	@Override
	public Icon getIcon() {
		if (icon == null) {
			icon = IconManager.getInstance().fetch(item.getIcon());
		}
		
		return icon;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getDescription()
	 */
	@Override
	public String getDescription() {
		return "TODO";
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getNameColor()
	 */
	@Override
	public Rarity getRarity() {
		return item.getRarity();
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getName()
	 */
	@Override
	public String getName() {
		return Bundles.items.get(item.getId());
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getAmount()
	 */
	@Override
	public int getAmount() {
		return amount;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ElementInfo.ElementData#getId()
	 */
	@Override
	public String getId() {
		return item.getId();
	}

}
