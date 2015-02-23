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
package de.bitbrain.craft.migration.jobs;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.graphics.Icon;
import de.bitbrain.craft.migration.Migrate;
import de.bitbrain.craft.migration.Migrations;
import de.bitbrain.craft.models.Item.Rarity;

/**
 * Migrates data at the beginning for new users
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ItemMigrationJob {

	@Migrate(Migrations.RELEASE)
	public void migrateItemsRelease(API api) {
		api.registerItem(ItemId.ACID_1, Icon.ACID_1, Rarity.COMMON, 1);
		api.registerItem(ItemId.ACID_2, Icon.ACID_2, Rarity.COMMON, 2);
		api.registerItem(ItemId.BENTAGON, Icon.BENTAGON, Rarity.RARE, 2);
		api.registerItem(ItemId.DARKSTONE, Icon.DARKSTONE, Rarity.SUPERIOR, 3);
		api.registerItem(ItemId.DUST, Icon.DUST, Rarity.COMMON, 1);
		api.registerItem(ItemId.FLUX, Icon.FLUX, Rarity.RARE, 1);
		api.registerItem(ItemId.GRAYSTONE, Icon.GRAYSTONE, Rarity.RARE, 1);
		api.registerItem(ItemId.JEWEL_DIAMOND_MEDIUM, Icon.JEWEL_DIAMOND_MEDIUM, Rarity.EPIC, 3);
		api.registerItem(ItemId.MERCURY, Icon.MERCURY, Rarity.COMMON, 2);
		api.registerItem(ItemId.MOLTEN_SAND, Icon.MOLTEN_SAND, Rarity.RARE, 2);
		api.registerItem(ItemId.PHIOLE_MEDIUM, Icon.PHIOLE_MEDIUM, Rarity.COMMON, 2);
		api.registerItem(ItemId.PHIOLE_SMALL, Icon.PHIOLE_SMALL, Rarity.COMMON, 1);
		api.registerItem(ItemId.SULFUR, Icon.SULFUR, Rarity.COMMON, 1);
		api.registerItem(ItemId.WATER, Icon.WATER, Rarity.COMMON, 1);
		api.registerItem(ItemId.XENOCITE, Icon.XENOCITE, Rarity.RARE, 2);		
	}

}
