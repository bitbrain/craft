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

package de.bitbrain.craft.db;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.files.FileHandle;

import de.bitbrain.craft.Settings;
import de.bitbrain.jpersis.drivers.Driver;
import de.bitbrain.jpersis.drivers.DriverException;
import de.bitbrain.jpersis.drivers.Query;
import de.bitbrain.jpersis.drivers.sqllite.SQLiteDriver;
import de.bitbrain.jpersis.util.Naming;

/**
 * Provides the database driver for this game
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class DriverProvider {
	
	private static LibGdxDriver driver;

	public static Driver getDriver() {
		driver = new LibGdxDriver();
		return driver;
	}
	public static void initialize() {
		if (driver != null && !driver.isLoaded()) {
			FileHandle handle = setup();
			driver.load(handle.file().getAbsolutePath());
		}
	}
	
	private static class LibGdxDriver implements Driver {
		
		private Driver driver;
		
		public void load(String path) {
			if (Gdx.app.getType().equals(ApplicationType.Android)) {
				driver = new SQLDroidDriver(path);
			} else {
				driver = new SQLiteDriver(path);
			}
		}
		
		public boolean isLoaded() {
			return driver != null;
		}

		/* (non-Javadoc)
		 * @see de.bitbrain.jpersis.drivers.Driver#close()
		 */
		@Override
		public void close() throws DriverException {
			if (driver != null) {
				driver.close();
			}
		}

		/* (non-Javadoc)
		 * @see de.bitbrain.jpersis.drivers.Driver#commit(de.bitbrain.jpersis.drivers.Query, java.lang.Class, java.lang.Object[], java.lang.Class, de.bitbrain.jpersis.util.Naming)
		 */
		@Override
		public Object commit(Query arg0, Class<?> arg1, Object[] arg2,
				Class<?> arg3, Naming arg4) throws DriverException {
			if (driver != null) {
				return driver.commit(arg0, arg1, arg2, arg3, arg4);
			} else {
				throw new RuntimeException("Driver is not initialized yet.");
			}
		}

		/* (non-Javadoc)
		 * @see de.bitbrain.jpersis.drivers.Driver#connect()
		 */
		@Override
		public void connect() throws DriverException {
			if (driver != null) {
				driver.connect();
			}
		}

		/* (non-Javadoc)
		 * @see de.bitbrain.jpersis.drivers.Driver#query(java.lang.Class, de.bitbrain.jpersis.util.Naming)
		 */
		@Override
		public Query query(Class<?> arg0, Naming arg1) {
			if (driver != null) {
				return driver.query(arg0, arg1);
			} else {
				throw new RuntimeException("Driver is not initialized yet.");
			}
		}
		
	}
	
	private static FileHandle setup() {
		FileHandle handle = Gdx.files.external(Settings.DIR_DATA + Settings.DATABASE);		
		try {
			if (!handle.file().getParentFile().exists()) {
				Gdx.app.log("INFO", "External directory not found. Creating a new one...");
				handle.file().getParentFile().mkdirs();
				Gdx.app.log("INFO", "Successfully created external directory.");
			} else {
				Gdx.app.log("INFO", "External directory found.");
			}
			if (!handle.file().exists()) {
				Gdx.app.log("INFO", "Datasource not found Create a new one..");
				handle.file().createNewFile();
			} else {
				Gdx.app.log("INFO", "Datasource found.");
			}
		} catch (IOException e) {
			Gdx.app.error("ERROR", "Could not create external directory.", e);
		}
		return handle;
	}
}
