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
import de.bitbrain.craft.core.Icon;
import de.bitbrain.craft.core.ItemId;
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
		api.registerItem(ItemId.ACID_1.getId(), Icon.ACID_1, Rarity.COMMON);
		api.registerItem(ItemId.ACID_2.getId(), Icon.ACID_2, Rarity.COMMON);
		api.registerItem(ItemId.BENTAGON.getId(), Icon.BENTAGON, Rarity.RARE);
		api.registerItem(ItemId.DARKSTONE.getId(), Icon.DARKSTONE, Rarity.SUPERIOR);
		api.registerItem(ItemId.DUST.getId(), Icon.DUST, Rarity.COMMON);
		api.registerItem(ItemId.FLUX.getId(), Icon.FLUX, Rarity.RARE);
		api.registerItem(ItemId.GRAYSTONE.getId(), Icon.GRAYSTONE, Rarity.RARE);
		api.registerItem(ItemId.JEWEL_DIAMOND_MEDIUM.getId(), Icon.JEWEL_DIAMOND_MEDIUM, Rarity.EPIC);
		api.registerItem(ItemId.MERCURY.getId(), Icon.MERCURY, Rarity.COMMON);
		api.registerItem(ItemId.MOLTEN_SAND.getId(), Icon.MOLTEN_SAND, Rarity.RARE);
		api.registerItem(ItemId.PHIOLE_MEDIUM.getId(), Icon.PHIOLE_MEDIUM, Rarity.COMMON);
		api.registerItem(ItemId.PHIOLE_SMALL.getId(), Icon.PHIOLE_SMALL, Rarity.COMMON);
		api.registerItem(ItemId.SULFUR.getId(), Icon.SULFUR, Rarity.COMMON);
		api.registerItem(ItemId.WATER.getId(), Icon.WATER, Rarity.COMMON);
		api.registerItem(ItemId.XENOCITE.getId(), Icon.XENOCITE, Rarity.RARE);		
	}

}