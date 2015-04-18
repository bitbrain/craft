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

import java.util.HashMap;
import java.util.Map;

import de.bitbrain.craft.models.Profession;

/** 
 * Builds an recipe
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class RecipeDataBuilder {
	
	private RecipeData data;
	
	public RecipeDataBuilder(ItemId result, Profession profession) {
		data = new RecipeData();
		result(result).profession(profession);
	}
	
	public RecipeDataBuilder profession(Profession profession) {
		data.profession = profession;
		return this;
	}
	
	public RecipeDataBuilder result(ItemId itemId) {
		data.itemId = itemId;
		return this;				
	}
	
	public RecipeDataBuilder amount(Integer amount) {
		data.amount = amount;
		return this;
	}
	
	public RecipeDataBuilder addIngredient(ItemId itemId, Integer amount) {
		data.ingredients.put(itemId, amount);
		return this;
	}
	
	public RecipeData build() {
		return data;
	}

	public static class RecipeData {
		
		// ID of the item to create
		ItemId itemId;
		
		// Amount of the items to create
		int amount = 1;
		
		// Profession which can learn this recipe
		Profession profession;
		
		// Ingredients which relate to the recipe
		Map<ItemId, Integer> ingredients = new HashMap<ItemId, Integer>();
	}
}
