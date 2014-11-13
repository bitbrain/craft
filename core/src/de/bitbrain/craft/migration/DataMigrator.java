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

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.google.inject.Inject;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.API.APIException;
import de.bitbrain.craft.core.Icon;
import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.db.ItemMapper;
import de.bitbrain.craft.db.MigrationMapper;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Item.Rarity;
import de.bitbrain.craft.models.Migration;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.models.PlayerUtils;
import de.bitbrain.jpersis.JPersis;

/**
 * Migrates data at the beginning for new users
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class DataMigrator {
	
	@Inject
	private API api;
	
	@Inject
	private JPersis jpersis;
	
	private MigrationMapper migrationMapper;
	
	private ItemMapper itemMapper;
	
	private List<MigrationJob> jobs = new ArrayList<MigrationJob>();
	
	@PostConstruct
	public void initMigrator() {
		migrationMapper = jpersis.map(MigrationMapper.class);
		itemMapper = jpersis.map(ItemMapper.class);
	}

	public void migrate() {
		try {

			Gdx.app.log("LOAD", "Load game data..");
			migratePlayer();
			migrateItems();
			migrateAll();
		} catch (APIException e) {
			Gdx.app.error("ERROR", "Unable to migrate data. " + e.getMessage());
		}
	}
	
	private void migratePlayer() throws APIException {
		Player p = api.getFirstPlayer();
		if (p == null) {
			Gdx.app.log("INFO", "No profile yet. Create a new one..");
			p = api.createPlayer("guest");
		} else {
			Gdx.app.log("INFO", "Player profile found.");
		}
		PlayerUtils.setCurrentPlayer(p);
	}
	
	private void migrateItems() {
		jobs.add(new MigrationJob() {
			public void migrate() {
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
			@Override
			public String getId() {
				return Migrations.RELEASE_ITEMS;
			}
		});
	}
	
	private void migrateAll() {
		for (MigrationJob job : jobs) {
			if (!migrationExists(job.getId())) {
				Gdx.app.log("INFO", "Migration '" + job.getId() + "' Did not happen. Migrate data..");
				job.migrate();
				addMigration(job.getId());
				Gdx.app.log("INFO", "Success migrating data for migration '" + job.getId() + "'!");
			} else {
				Gdx.app.log("INFO", "Migration '" + job.getId() + "' found.");
			}
		}
	}
	
	private boolean migrationExists(String migrationId) {
		Player p = Player.getCurrent();
		return migrationMapper.findByPlayerId(p.getId(), Migrations.RELEASE_ITEMS) != null;
	}
	
	private void addMigration(String migrationId) {
		Player p = Player.getCurrent();
		migrationMapper.insert(new Migration(Migrations.RELEASE_ITEMS, p.getId()));
	}
	
	private interface MigrationJob {
		
		String getId();
		
		void migrate();
	}
}
