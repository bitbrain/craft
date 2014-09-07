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
 * Ids of all items
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public enum ItemId {
	
	FLUX("item_flux"),
	ACID_1("item_acid_1"),
	ACID_2("item_acid_2"),
	DUST("item_dust"),
	WATER("item_water"),
	SULFUR("item_sulfur"),
	MERCURY("item_mercury"),
	PHIOLE_SMALL("item_phiole_small"),
	PHIOLE_MEDIUM("item_phiole_medium"),
	GRAYSTONE("item_graystone"),
	XENOCITE("item_xenocite"),
	BENTAGON("item_bentagon");
	
	private String id;
	
	ItemId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}