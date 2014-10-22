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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.sql.Statement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Settings;
import de.bitbrain.jpersis.MapperManager;
import de.bitbrain.jpersis.db.DatabaseConnector;
import de.bitbrain.jpersis.db.DatabaseException;
import de.bitbrain.jpersis.db.SQLiteConnector;

/**
 * Helper for database connection
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class DatabaseHelper {
	
	public static boolean setConnector() {
		FileHandle handle = Gdx.files.external(Settings.DIR_DATA + Settings.DATABASE);
		boolean folderExisted = true;
		
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
				folderExisted = false;
			} else {
				Gdx.app.log("INFO", "Datasource found.");
			}
		} catch (IOException e) {
			Gdx.app.error("ERROR", "Could not create external directory.", e);
		}
		
		Gdx.app.log("INFO", "Connecting to database...");
		String databasePath = handle.file().getAbsolutePath();
		DatabaseConnector connector = new SQLiteConnector(databasePath);
		MapperManager.setDefaultConnector(connector);
		return folderExisted;
	}

	public static void connect() {		
		try {
			boolean folderExisted = setConnector();
			MapperManager.getInstance().getConnector().openConnection();

			// Create a new database if not exists
			if (!folderExisted) {
				Statement statement = MapperManager.getInstance().getConnector().getStatement();
				FileHandle init = Gdx.files.internal(Assets.SQL_INIT);
				exectuteScript(init.reader(), statement);
				init = Gdx.files.internal(Assets.SQL_UPDATE);
				exectuteScript(init.reader(), statement);
			}
			Gdx.app.log("INFO", "Connected to database.");
		} catch (DatabaseException e) {
			e.printStackTrace();
			Gdx.app.error("ERROR", "Could not connect to database.", e);
		} catch (IOException e) {
			e.printStackTrace();
			Gdx.app.error("ERROR", "Could not connect to database.", e);
		} catch (SQLException e) {
			e.printStackTrace();
			Gdx.app.error("ERROR", "Could not create database.", e);
		}
	}
	
	private static boolean exectuteScript(Reader reader, Statement stmt) throws IOException,SQLException {
		
		boolean isScriptExecuted = false;

		BufferedReader in = new BufferedReader(reader);
		String str;
		StringBuffer sb = new StringBuffer();
			
		while ((str = in.readLine()) != null) {
			sb.append(str + "\n ");
		}
		in.close();
		stmt.executeUpdate(sb.toString());
		isScriptExecuted = true;
		
		return isScriptExecuted;
	}

}
