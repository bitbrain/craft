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

import java.util.Collection;
import java.util.List;

import de.bitbrain.craft.audio.SoundType;
import de.bitbrain.craft.core.RecipeDataBuilder.RecipeData;
import de.bitbrain.craft.graphics.Icon;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Item.Rarity;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.models.Progress;
import de.bitbrain.craft.models.Recipe;
import de.bitbrain.craft.models.SoundConfig;

/**
 * Overall craft API interface
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface API {
	
	/**
	 * Returns the item with the given ID
	 * 
	 * @param id item indicator
	 * @return found item. Is null if nothing can be found
	 */
	Item getItem(ItemId id);
	
	/**
	 * Provides all available items
	 * 
	 * @return a collection of all items
	 */
	Collection<Item> getAllItems();
	
	/**
	 * Provides all items which are owned by the given player. Contains also
	 * items which can be possibly crafted but are not really there.
	 * 
	 * @param playerId id of the player
	 * @return owned items by player
	 */
	ItemBag getOwnedItems(int playerId);
	
	/**
	 * Adds a single new item and provides it
	 * 
	 * @param playerId id of the items owner
	 * @param id id of the item
	 * @return newly added item
	 */
	Item addItem(int playerId, ItemId id) ;
	
	/**
	 * Adds multiple items and provides it
	 * 
	 * @param playerId id of the player
	 * @param id id of the item
	 * @param amount number of items to add
	 * @return newly added items
	 */
	Item addItem(int playerId, ItemId id, int amount);
	
	/**
	 * Removes the item from a player
	 * 
	 * @param playerId owner of the item
	 * @param id item string id
	 * @param amount 
	 * @return returns true if removed and false if not.
	 */
	boolean removeItem(int playerId, ItemId id, int amount);
	
	/**
	 * Returns the first found player
	 * 
	 * @return first found player
	 */
	Player getFirstPlayer();
	
	/**
	 * Creates a new player in the datastore and provides it
	 * 
	 * @param name name of the new player
	 * @return newly created player
	 * @throws APIException Is thrown if player already exists
	 */
	Player createPlayer(String name) throws APIException;
	
	/**
	 * Returns true when this item is craftable from any profession
	 * 
	 * @param itemId
	 * @return
	 */
	boolean canCraftIndirect(ItemId itemId);
	
	/**
	 * Checks if the current player can craft the item with the current profession
	 * 
	 * @param itemId
	 * @return
	 */
	boolean canCraft(ItemId itemId);

	/**
	 * Determines if the player can craft the given item 
	 * 
	 * @param player
	 * @param itemId
	 * @return
	 */
	boolean canCraft(Player player, ItemId itemId);
	
	/**
	 * Determines if the player can craft the given item and the profession
	 * 
	 * @param player
	 * @param itemId
	 * @return
	 */
	boolean canCraft(Player player, Profession profession, ItemId itemId);
	

	/**
	 * Registers a new icon to the system. Only works if itemId is not taken yet
	 * 
	 * @param itemId id of the new item
	 * @param icon icon of the item
	 * @param rarity rarity of the item
	 */
	void registerItem(ItemId itemId, Icon icon, Rarity rarity, int level);
	
	/**
	 * Applies a new sound type to an existing item
	 * 
	 * @param itemId target item
	 * @param deferredSoundId sound file name
	 * @param type event type when the sound should occur
	 * @param pitch pitching of the sound
	 */
	void applyItemSound(ItemId itemId, String deferredSoundId, SoundType type, float pitch);
	
	/**
	 * Gets an id of an item sound related to the sound type
	 * 
	 * @param itemId
	 * @param type
	 * @return
	 */
	SoundConfig getItemSoundConfig(ItemId itemId, SoundType type);
	

	/**
	 * Removes an item completely of the player
	 * 
	 * @param playerId
	 * @param id
	 */
	void removeItem(int playerId, ItemId id);

	/**
	 * Removes all owned items of the player
	 * 
	 * @param playerId
	 */
	void clearItems(int playerId);
	
	/**
	 * Adds a new recipe to the system
	 * 
	 * @param data recipe data
	 */
	Recipe registerRecipe(RecipeData data);
	
	/**
	 * Teach the player to craft the given item
	 * 
	 * @param player player 
	 * @param id item id
	 * @return true if learned
	 */
	boolean learnRecipe(Player player, ItemId id);
	
	/**
	 * Teach the current player to craft the given item
	 * 
	 * @param id item id
	 * @return true if learned
	 */
	boolean learnRecipe(ItemId id);
	
	/**
	 * Finds a recipe which can craft the item
	 * 
	 * @param itemId
	 * @return
	 */
	Recipe findRecipe(ItemId itemId);

	/**
	 * Finds materials of the item. If no recipe can be found, the bag will be empty
	 * 
	 * @param item item
	 * @return
	 */
	ItemBag findIngredients(Item item);
	
	/**
	 * Returns the amount of owned items 
	 * 
	 * @param item
	 * @return
	 */
	int getItemAmount(Item item);
	
	/**
	 * Gets a snapshot of the current progress
	 * 
	 * @param profession
	 * @return
	 */
	Progress getProgress(Profession profession);
	
	/**
	 * Adds xp to the given profession
	 * 
	 * @param profession the professsion which should be affected
	 * @param xp amount of xp to add
	 * @return updated progress model
	 */
	Progress addXp(Profession profession, int xp);
	
	/**
	 * Sets the complete xp of a profession
	 * 
	 * @param profession the professsion which should be affected
	 * @param xp amount of xp to set
	 * @return updated progress model
	 */
	Progress setXp(Profession profession, int xp);
	
	/**
	 * Filters out all possible recipes which have the given items as ingredients in common.
	 * 
	 * @param ingredients list of items
	 * @return new list of recipes. Is empty if no recipes could be found.
	 */
	List<Recipe> getRecipesByIngredients(Collection<Item> items);
	
	/**
	 * Is thrown as an API error occurs
	 *
	 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
	 * @since 1.0
	 * @version 1.0
	 */
	public static class APIException extends Exception {
		
		private static final long serialVersionUID = 1L;

		public APIException(String message) {
			super(message);
		}
	}
}
