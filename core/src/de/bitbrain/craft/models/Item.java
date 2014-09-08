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

package de.bitbrain.craft.models;

import com.badlogic.gdx.graphics.Color;

import de.bitbrain.craft.util.Identifiable;

/**
 * Item which can be used for crafting
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Item implements Identifiable {

	private String id = "";
	
	private String icon = "";
	
	private Rarity rarity = Rarity.COMMON;
	
	private int level = 1;
	
	public String getIcon() {
		return icon;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Rarity getRarity() {
		return rarity;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id + ", " + icon + ", " + level + ", " + rarity;
	}
	
	public static enum Rarity {
		COMMON("ffffff"),
		RARE("00ff00"),
		SUPERIOR("0000ff"),
		EPIC("5500ff"),
		UNIQUE("ff6600"),
		LEGENDARY("ff00ff");
		
		private Color color;
		
		Rarity(String color) {
			this.color = Color.valueOf(color);
		}
		
		public Color getColor() {
			return color;
		}
	}
}
