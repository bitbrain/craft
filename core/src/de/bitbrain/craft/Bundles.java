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

package de.bitbrain.craft;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * Contains all language bundles
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class Bundles {

	public static I18NBundle general, items, recipes;
	
	private static FileHandle generalHandle;
	private static FileHandle itemHandle;
	private static FileHandle recipesHandle;
	
	static void load() {		

		generalHandle = Gdx.files.internal(Assets.BDL_GENERAL);
		itemHandle = Gdx.files.internal(Assets.BDL_ITEMS);
		recipesHandle = Gdx.files.internal(Assets.BDL_RECIPES);
		
		Locale locale = new Locale("en");		
		setLocale(locale);
	}
	
	public static void setLocale(Locale locale) {
		general = I18NBundle.createBundle(generalHandle, locale);
		items = I18NBundle.createBundle(itemHandle, locale);
		recipes = I18NBundle.createBundle(recipesHandle, locale);
	}
	
	// KEYS
	
	public static final String START = "play";
	public static final String CREDITS = "credits";
	public static final String ALCHEMIST = "alchemist";
	public static final String ENGINEER = "engineer";
	public static final String JEWELER = "jeweler";
}
