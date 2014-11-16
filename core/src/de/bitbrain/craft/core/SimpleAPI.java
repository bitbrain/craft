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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import de.bitbrain.craft.db.ItemMapper;
import de.bitbrain.craft.db.OwnedItemMapper;
import de.bitbrain.craft.db.PlayerMapper;
import de.bitbrain.craft.db.ProgressMapper;
import de.bitbrain.craft.events.ElementEvent;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Item.Rarity;
import de.bitbrain.craft.models.OwnedItem;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.models.Progress;
import de.bitbrain.craft.models.Recipe;
import de.bitbrain.jpersis.JPersis;

/**
 * General API interface
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
class SimpleAPI implements API {

	private ItemMapper itemMapper;
	private OwnedItemMapper ownedItemMapper;
	private PlayerMapper playerMapper;
	private ProgressMapper progressMapper;
	
	@Inject
	private JPersis jpersis;
	
	@PostConstruct
	public void init() {
		itemMapper = jpersis.map(ItemMapper.class);
		ownedItemMapper = jpersis.map(OwnedItemMapper.class);
		playerMapper = jpersis.map(PlayerMapper.class);
		progressMapper = jpersis.map(ProgressMapper.class);
	}
	
	@Override
	public Item getItem(ItemId id) {
		return getItem(id.getId());
	}
	
	public Item getItem(String id) {
		return itemMapper.findById(id);
	}
	
	/**
	 * Provides all available items
	 * 
	 * @return a collection of all items
	 */
	@Override
	public Collection<Item> getAllItems() {
		return itemMapper.findAll();
	}
	
	/**
	 * Provides all items which are owned by the given player
	 * 
	 * @param playerId id of the player
	 * @return owned items by player
	 */
	@Override
	public Map<Item, Integer> getOwnedItems(int playerId) {
		Collection<OwnedItem> owned = ownedItemMapper.findAllByPlayerId(playerId);
		Map<Item, Integer> items = new HashMap<Item, Integer>();
		
		for	(OwnedItem own : owned) {
			Item item = itemMapper.findById(own.getItemId());
			items.put(item, own.getAmount());
		}
		
		return items;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.core.API#getRecipes(de.bitbrain.craft.models.Profession)
	 */
	@Override
	public Collection<Recipe> getRecipes(Profession profession) {
		// TODO
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		Recipe recipe = new Recipe();
		recipe.setDescription("A basic recipe");
		recipe.setName("Acid 1");
		recipe.setId("recipe_xenocite");
		recipe.setProductId("");
		recipe.setIcon(Icon.XENOCITE);
		recipes.add(recipe);		
		return recipes;
	}
	
	/**
	 * Returns the first found player
	 * 
	 * @return first found player
	 */
	@Override
	public Player getFirstPlayer() {
		Collection<Player> players = playerMapper.findAll();		
		if (players.size() > 0) {
			return players.iterator().next();
		} else {
			return null;
		}
	}
	
	@Override
	public Player createPlayer(String name) throws APIException {
		if (playerMapper.findByName(name) == null) {
			Player player = new Player();
			player.setName(name);
			playerMapper.insert(player);
			
			// Add professions
			for (Profession profession : Profession.values()) {
				progressMapper.insert(new Progress(player.getId(), profession));
			}
			
			return player;
		} else {
			throw new APIException("Unable to create player. Player with name '" + name + "' already exists.");
		}
	}
	
	@Override
	public Item addItem(int playerId, ItemId id) {
		return addItem(playerId, id, 1);
	}
	
	@Override
	public Item addItem(int playerId, ItemId id, int amount) {
		Item item = getItem(id);
		
		if (item != null) {
			
			OwnedItem owned = ownedItemMapper.findById(id.getId(), playerId);
			
			if (owned == null) {
				owned = new OwnedItem();
				owned.setPlayerId(playerId);
				owned.setItemId(item.getId());
				owned.setAmount(amount);
				ownedItemMapper.insert(owned);
			} else {
				owned.setAmount(owned.getAmount() + amount);
				ownedItemMapper.update(owned);
			}
			return item;
		} else {
			return null;
		}
	}
	
	@Override
	public boolean removeItem(int playerId, String id, int amount) {
		OwnedItem owned = ownedItemMapper.findById(id, playerId);
		int count = owned.getAmount() - amount;
		if (owned != null && count >= 0) {
			Item item = itemMapper.findById(id);
			try {
			if (count > 0) {
				owned.setAmount(count);
				//ownedItemMapper.update(owned);
			} else {
				//ownedItemMapper.delete(owned);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			EventBus bus = SharedInjector.get().getInstance(EventBus.class);
			bus.fireEvent(new ElementEvent<Item>(EventType.REMOVE, item, amount));

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isItemId(String id) {
		return id.startsWith("item_");		
	}
	
	@Override
	public boolean isRecipeId(String id) {
		return id.startsWith("recipe_");		
	}

	@Override
	public void registerItem(String itemId, Icon icon, Rarity rarity) {
		Item item = itemMapper.findById(itemId);		
		if (item == null) {
			item = new Item(itemId, icon, rarity);
			itemMapper.insert(item);
		}
	}

	@Override
	public boolean canCraft(Player player, String itemId) {
		return true;
	}
}