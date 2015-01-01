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

import de.bitbrain.craft.core.Icon;
import de.bitbrain.craft.graphics.Effect;
import de.bitbrain.craft.util.Identifiable;
import de.bitbrain.jpersis.annotations.PrimaryKey;

/**
 * Item which can be used for crafting
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Item implements Identifiable {

	@PrimaryKey
	private String id = "";
	
	private Icon icon;
	
	private Rarity rarity = Rarity.COMMON;
	
	private Class<? extends Effect> effect = Effect.class;
	
	public Item() {
		
	}
	
	public Item(String id, Icon icon, Rarity rarity) {
		this.id = id;
		this.icon = icon;
		this.rarity = rarity;
	}
	
	public Icon getIcon() {
		return icon;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public Rarity getRarity() {
		return rarity;
	}
	
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}
	
	public void setEffect(Class<? extends Effect> effect) {
		this.effect = effect;
	}
	
	public Class<? extends Effect> getEffect() {
		return effect;
	}
	
	public static enum Rarity {
		COMMON("dddddd"),
		RARE("00ff00"),
		SUPERIOR("2211cc"),
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
