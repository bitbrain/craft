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
import de.bitbrain.craft.migration.Migrate;
import de.bitbrain.craft.migration.Migrations;
import de.bitbrain.craft.models.Player;

/**
 * Migrates data at the beginning for new users
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class OwnedItemMigrationJob {

	@Migrate(Migrations.RELEASE)
	public void migrateOwnedItemsRelease(API api) {
		Player p = Player.getCurrent();
		api.addItem(p.getId(), ItemId.ACID_1, 10);
		api.addItem(p.getId(), ItemId.XENOCITE, 6);
		api.addItem(p.getId(), ItemId.FLUX, 2);
		api.addItem(p.getId(), ItemId.WATER, 2);
		api.addItem(p.getId(), ItemId.SULFUR, 22);
		api.addItem(p.getId(), ItemId.JEWEL_DIAMOND_MEDIUM, 2);
	}

}
