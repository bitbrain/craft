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

import de.bitbrain.jpersis.annotations.PrimaryKey;

/**
 * Object representation of a learned recipe
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class LearnedRecipe {

	@PrimaryKey(true)
	private int id;
	
	private String recipeId;
	
	private int playerId;
	
	public LearnedRecipe() {
		
	}
	
	public LearnedRecipe(String recipeId, int playerId) {
		this.recipeId = recipeId;
		this.playerId = playerId;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @param itemId the itemId to set
	 */
	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}
	
	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return the itemId
	 */
	public String getRecipeId() {
		return recipeId;
	}
	
	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + playerId;
		result = prime * result
				+ ((recipeId == null) ? 0 : recipeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LearnedRecipe other = (LearnedRecipe) obj;
		if (playerId != other.playerId)
			return false;
		if (recipeId == null) {
			if (other.recipeId != null)
				return false;
		} else if (!recipeId.equals(other.recipeId))
			return false;
		return true;
	}
	
	
}
