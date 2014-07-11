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

import com.badlogic.gdx.graphics.Color;


/**
 * Resource file which contains all resource keys
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class Assets {
	
	// ---------- GENERAL --------
	
	/**
	 * Texture folderpath
	 */
	public static final String FOLDER_IMAGES = "images/";
	
	/**
	 * Font folderpath
	 */
	public static final String FOLDER_FONTS = "fonts/";
	
	/**
	 * Audio folderpath
	 */
	public static final String FOLDER_AUDIO = "audio/";
	
	// ---------- FONTS -----------	
	
	/**
	 * 8bit font (small)
	 */
	public static final String FONT_SMALL = FOLDER_FONTS + "small.fnt";
	
	/**
	 * 8bit font (medium)
	 */
	public static final String FONT_MEDIUM = FOLDER_FONTS + "medium.fnt";
	
	/**
	 * 8bit font (larger)
	 */
	public static final String FONT_LARGER = FOLDER_FONTS + "larger.fnt";
	
	/**
	 * 8bit font (big)
	 */
	public static final String FONT_BIG = FOLDER_FONTS + "big.fnt";

	// ---------- SOUNDS ----------
	
	public static final String SOUND_BUTTON_01 = FOLDER_AUDIO + "button_01.wav";	
	
	
	// ---------- MUSIC ----------
	
	public static final String MUSIC_MENU = FOLDER_AUDIO + "menu.mp3";	
	
	
	// ---------- TEXTURES --------

	/**
	 * Background image (brown)
	 */
	public static final String TEXTURE_BACKGROUND = FOLDER_IMAGES + "background.png";
	
	/**
	 * Craft logo (medium)
	 */
	public static final String TEXTURE_LOGO = FOLDER_IMAGES + "logo.png";
	
	/**
	 * button (unpressed)
	 */
	public static final String TEXTURE_BUTTON = FOLDER_IMAGES + "button.png";
	
	/**
	 * button (pressed)
	 */
	public static final String TEXTURE_BUTTON_DARK = FOLDER_IMAGES + "button_down.png";
	
	// ---------- COLORS ----------
	
	/**
	 * grass green color
	 */
	public static final Color COLOR_GREEN_GRASS = Color.valueOf("b8d41c");
	public static final Color COLOR_GREEN_GRASS_LIGHT = Color.valueOf("c7e03d");
	public static final Color COLOR_WOOD_TEAK = Color.valueOf("6a2b05");
}
