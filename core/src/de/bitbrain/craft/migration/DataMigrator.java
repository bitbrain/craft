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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.badlogic.gdx.Gdx;
import com.google.inject.Inject;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.API.APIException;
import de.bitbrain.craft.db.MigrationMapper;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.migration.jobs.ItemMigrationJob;
import de.bitbrain.craft.migration.jobs.OwnedItemMigrationJob;
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
	
	private Class<?>[] targets;
	
	@PostConstruct
	public void initMigrator() {
		migrationMapper = jpersis.map(MigrationMapper.class);
		targets = getMigrators();
	}

	public void migrate() {
		try {
			Gdx.app.log("LOAD", "Load game data..");
			migratePlayer();
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
	
	private void migrateAll() {
		for (Class<?> c : targets) {
			Object o = createMigrator(c);
			Method[] methods = o.getClass().getDeclaredMethods();
			for (Method m : methods) {
				if (m.isAnnotationPresent(Migrate.class)) {
					Migrate migrate = m.getAnnotation(Migrate.class);
					migrateSingle(c.getName() + "::" + migrate.value(), o, m);
				}
			}
		}
	}
	
	private Object createMigrator(Class<?> c) {
		try {
			return c.getConstructor().newInstance();
		} catch (InstantiationException e) {
			throw new MigrateException(e);
		} catch (IllegalAccessException e) {
			throw new MigrateException(e);
		} catch (IllegalArgumentException e) {
			throw new MigrateException(e);
		} catch (InvocationTargetException e) {
			throw new MigrateException(e);
		} catch (NoSuchMethodException e) {
			throw new MigrateException(e);
		} catch (SecurityException e) {
			throw new MigrateException(e);
		}
	}
	
	private void migrateSingle(String id, Object o, Method m) {
		if (!migrationExists(id)) {
			Gdx.app.log("INFO", "Migration '" + id + "' Did not happen. Migrate data..");
			try {
				m.invoke(o, jpersis, api);
			} catch (IllegalAccessException e) {
				throw new MigrateException(e);
			} catch (IllegalArgumentException e) {
				throw new MigrateException(e);
			} catch (InvocationTargetException e) {
				throw new MigrateException(e);
			}
			addMigration(id);
			Gdx.app.log("INFO", "Success migrating data for migration '" + id + "'!");
		} else {
			Gdx.app.log("INFO", "Migration '" + id + "' found.");
		}
	}
	
	private boolean migrationExists(String migrationId) {
		Player p = Player.getCurrent();
		return migrationMapper.findByPlayerId(p.getId(), migrationId) != null;
	}
	
	private void addMigration(String migrationId) {
		Player p = Player.getCurrent();
		migrationMapper.insert(new Migration(migrationId, p.getId()));
	}
	
	private Class<?>[] getMigrators() {
		return new Class<?>[]{
				ItemMigrationJob.class, OwnedItemMigrationJob.class
		};
	}
	
	private class MigrateException extends RuntimeException {
		
		private static final long serialVersionUID = 1L;

		public MigrateException(Throwable t) {
			super(t);
		}
	}
}
