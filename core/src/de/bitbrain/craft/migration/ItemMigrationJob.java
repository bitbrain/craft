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
package de.bitbrain.craft.migration;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.Icon;
import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.db.ItemMapper;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Item.Rarity;
import de.bitbrain.jpersis.JPersis;

/**
 * Migrates data at the beginning for new users
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ItemMigrationJob {

	@Migrate(Migrations.RELEASE_ITEMS)
	public void migrateItems(JPersis jpersis, API api) {
		ItemMapper itemMapper = jpersis.map(ItemMapper.class);
		itemMapper.insert(new Item(ItemId.ACID_1.getId(), Icon.ACID_1, Rarity.COMMON));
		itemMapper.insert(new Item(ItemId.ACID_2.getId(), Icon.ACID_2, Rarity.COMMON));
		itemMapper.insert(new Item(ItemId.BENTAGON.getId(), Icon.BENTAGON, Rarity.RARE));
		itemMapper.insert(new Item(ItemId.DARKSTONE.getId(), Icon.DARKSTONE, Rarity.SUPERIOR));
		itemMapper.insert(new Item(ItemId.DUST.getId(), Icon.DUST, Rarity.COMMON));
		itemMapper.insert(new Item(ItemId.FLUX.getId(), Icon.FLUX, Rarity.RARE));
		itemMapper.insert(new Item(ItemId.GRAYSTONE.getId(), Icon.GRAYSTONE, Rarity.RARE));
		itemMapper.insert(new Item(ItemId.JEWEL_DIAMOND_MEDIUM.getId(), Icon.JEWEL_DIAMOND_MEDIUM, Rarity.EPIC));
		itemMapper.insert(new Item(ItemId.MERCURY.getId(), Icon.MERCURY, Rarity.COMMON));
		itemMapper.insert(new Item(ItemId.MOLTEN_SAND.getId(), Icon.MOLTEN_SAND, Rarity.RARE));
		itemMapper.insert(new Item(ItemId.PHIOLE_MEDIUM.getId(), Icon.PHIOLE_MEDIUM, Rarity.COMMON));
		itemMapper.insert(new Item(ItemId.PHIOLE_SMALL.getId(), Icon.PHIOLE_SMALL, Rarity.COMMON));
		itemMapper.insert(new Item(ItemId.SULFUR.getId(), Icon.SULFUR, Rarity.COMMON));
		itemMapper.insert(new Item(ItemId.WATER.getId(), Icon.WATER, Rarity.COMMON));
		itemMapper.insert(new Item(ItemId.XENOCITE.getId(), Icon.XENOCITE, Rarity.RARE));		
	}

}
