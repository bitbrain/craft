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

package de.bitbrain.craft.core;

/**
 * Contains all supported icons
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public enum Icon {
	
	FLUX,
	ACID_1,
	ACID_2,
	DUST,
	WATER,
	SULFUR,
	MERCURY,
	PHIOLE_SMALL,
	PHIOLE_MEDIUM,
	GRAYSTONE,
	BENTAGON,
	DARKSTONE,
	MOLTEN_SAND, 
	RECIPE, 
	JEWEL_DIAMOND_MEDIUM,
	XENOCITE;
	
	
	public final String EXTENSION = ".png";
	
	public String getId() {
		return "ico_" + name().toLowerCase();
	}
	
	public String getFile() {
		return getId() + ".png";
	}
}

