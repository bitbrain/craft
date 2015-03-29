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
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.google.inject.Inject;

import de.bitbrain.craft.audio.SoundType;
import de.bitbrain.craft.core.RecipeDataBuilder.RecipeData;
import de.bitbrain.craft.db.GoalMapper;
import de.bitbrain.craft.db.IngredientMapper;
import de.bitbrain.craft.db.ItemMapper;
import de.bitbrain.craft.db.ItemSoundMapper;
import de.bitbrain.craft.db.LearnedRecipeMapper;
import de.bitbrain.craft.db.OwnedItemMapper;
import de.bitbrain.craft.db.PlayerMapper;
import de.bitbrain.craft.db.ProgressMapper;
import de.bitbrain.craft.db.RecipeMapper;
import de.bitbrain.craft.db.SoundConfigMapper;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.ItemEvent;
import de.bitbrain.craft.events.ProgressEvent;
import de.bitbrain.craft.graphics.Icon;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Goal;
import de.bitbrain.craft.models.Ingredient;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Item.Rarity;
import de.bitbrain.craft.models.ItemSound;
import de.bitbrain.craft.models.LearnedRecipe;
import de.bitbrain.craft.models.OwnedItem;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.models.Progress;
import de.bitbrain.craft.models.Recipe;
import de.bitbrain.craft.models.SoundConfig;
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
	private RecipeMapper recipeMapper;
	private LearnedRecipeMapper learnedRecipeMapper;
	private GoalMapper goalMapper;
	private IngredientMapper ingredientMapper;
	private SoundConfigMapper soundConfigMapper;
	private ItemSoundMapper itemSoundMapper;
	
	@Inject
	private JPersis jpersis;

	@PostConstruct
	public void init() {
		itemMapper = jpersis.map(ItemMapper.class);
		ownedItemMapper = jpersis.map(OwnedItemMapper.class);
		playerMapper = jpersis.map(PlayerMapper.class);
		progressMapper = jpersis.map(ProgressMapper.class);
		recipeMapper = jpersis.map(RecipeMapper.class);
		learnedRecipeMapper = jpersis.map(LearnedRecipeMapper.class);
		goalMapper = jpersis.map(GoalMapper.class);
		ingredientMapper = jpersis.map(IngredientMapper.class);
		soundConfigMapper = jpersis.map(SoundConfigMapper.class);
		itemSoundMapper = jpersis.map(ItemSoundMapper.class);
	}

	@Override
	public Item getItem(ItemId id) {
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
	 * @param playerId
	 *            id of the player
	 * @return owned items by player
	 */
	@Override
	public ItemBag getOwnedItems(int playerId) {
		ItemBag bag = new ItemBag();
		Collection<OwnedItem> owned = ownedItemMapper
				.findAllByPlayerId(playerId);

		for (OwnedItem own : owned) {
			Item item = itemMapper.findById(own.getItemId());
			bag.add(item, own.getAmount());
		}

		// TODO: Implement later when system is more flexible
		Collection<LearnedRecipe> learnedRecipes = learnedRecipeMapper
				.findByPlayerId(playerId);
		for (LearnedRecipe learned : learnedRecipes) {
			Recipe recipe = recipeMapper.findById(learned.getRecipeId());
			Item item = itemMapper.findById(recipe.getItemId());
			if (recipe.getProfession().equals(Profession.current)
					&& !bag.contains(item)) {
				bag.add(item, 0);
			}
		}

		return bag;
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
			throw new APIException(
					"Unable to create player. Player with name '" + name
							+ "' already exists.");
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
			OwnedItem owned = ownedItemMapper.findById(id, playerId);
			if (owned == null) {
				owned = new OwnedItem();
				owned.setPlayerId(playerId);
				owned.setItemId(id);
				owned.setAmount(amount);
				ownedItemMapper.insert(owned);
			} else {
				if (owned.getAmount() == Item.INFINITE_AMOUNT) {
					return item;
				}
				owned.setAmount(owned.getAmount() + amount);
				ownedItemMapper.update(owned);
			}
			bus().fireEvent(new ItemEvent(EventType.ADD, item, amount));
			return item;
		} else {
			return null;
		}
	}

	@Override
	public boolean removeItem(int playerId, ItemId id, int amount) {
		OwnedItem owned = ownedItemMapper.findById(id, playerId);
		Item item = itemMapper.findById(id);
		EventBus eventBus = SharedInjector.get()
				.getInstance(EventBus.class);
		if (owned.getAmount() == Item.INFINITE_AMOUNT) {
			eventBus.fireEvent(new ItemEvent(EventType.REMOVE, item, amount));
			return true;
		}
		int count = owned.getAmount() - amount;
		if (owned != null && count >= 0) {
			
			try {
				if (count > 0) {
					owned.setAmount(count);
					ownedItemMapper.update(owned);
				} else {
					ownedItemMapper.delete(owned);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			eventBus.fireEvent(new ItemEvent(EventType.REMOVE, item, amount));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void registerItem(ItemId itemId, Icon icon, Rarity rarity, int level) {
		Item item = itemMapper.findById(itemId);
		if (item == null) {
			item = new Item(itemId, icon, rarity);
			item.setLevel(level);
			itemMapper.insert(item);
		}
	}

	@Override
	public boolean canCraftIndirect(ItemId itemId) {
		for (Profession p : Profession.values()) {
			if (canCraft(Player.getCurrent(), p, itemId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canCraft(ItemId itemId) {
		return canCraft(Player.getCurrent(), itemId);
	}

	@Override
	public boolean canCraft(Player player, Profession profession, ItemId itemId) {
		Recipe recipe = recipeMapper.findByItemId(itemId);
		if (recipe != null && recipe.getProfession().equals(profession)) {
			LearnedRecipe learned = learnedRecipeMapper.findByRecipeId(
					recipe.getId(), player.getId());
			return learned != null;
		}
		return false;
	}

	@Override
	public boolean canCraft(Player player, ItemId itemId) {
		return canCraft(player, Profession.current, itemId);
	}

	@Override
	public void removeItem(int playerId, ItemId id) {
		OwnedItem owned = ownedItemMapper.findById(id, playerId);
		ownedItemMapper.delete(owned);
		bus().fireEvent(
				new ItemEvent(EventType.REMOVE, getItem(owned.getItemId()),
						owned.getAmount()));
	}

	@Override
	public void clearItems(int playerId) {
		Collection<OwnedItem> items = ownedItemMapper
				.findAllByPlayerId(playerId);
		ownedItemMapper.delete(items);
		for (OwnedItem item : items) {
			bus().fireEvent(
					new ItemEvent(EventType.REMOVE, getItem(item.getItemId()),
							item.getAmount()));
		}
	}

	@Override
	public void applyItemSound(ItemId itemId, String deferredSoundFile,
			SoundType type, float pitch) {
		Collection<ItemSound> itemSounds = itemSoundMapper.findByItemId(itemId);
		boolean found = false;
		for (ItemSound itemSound : itemSounds) {
			SoundConfig config = soundConfigMapper.findById(itemSound.getSoundConfigId());
			if (config.getFile().equals(deferredSoundFile) && config.getType().equals(type)) {
				config.setPitch(pitch);
				soundConfigMapper.update(config);
				found = true;
				break;
			}
		}
		if (!found) {
			SoundConfig config = new SoundConfig(deferredSoundFile, type);
			config.setPitch(pitch);
			config.setPan(1f);
			config.setVolume(0.6f);
			soundConfigMapper.insert(config);
			ItemSound itemSound = new ItemSound();
			itemSound.setItemId(itemId);
			itemSound.setSoundConfigId(config.getId());
			itemSoundMapper.insert(itemSound);
		}
	}

	@Override
	public SoundConfig getItemSoundConfig(ItemId itemId, SoundType type) {
		Collection<ItemSound> itemSounds = itemSoundMapper.findByItemId(itemId);
		for (ItemSound sound : itemSounds) {
			SoundConfig config = soundConfigMapper.findById(sound.getSoundConfigId());
			if (config.getType().equals(type)) {
				return config;
			}
		}
		return null;
	}

	@Override
	public Recipe registerRecipe(RecipeData data) {
		Recipe recipe = new Recipe();
		recipe.setItemId(data.itemId);
		recipe.setAmount(data.amount);
		recipe.setProfession(data.profession);
		if (recipeMapper.insert(recipe)) {
			// Add ingredients
			for (Entry<ItemId, Integer> entry : data.ingredients.entrySet()) {
				Ingredient i = new Ingredient();
				ItemId itemId = entry.getKey();
				i.setItemId(itemId);
				i.setAmount(entry.getValue());
				i.setRecipeId(recipe.getId());
				if (!ingredientMapper.insert(i)) {
					Gdx.app.error(
							"ERROR",
							"Unable to register recipe: " + itemId
									+ " already exists for recipe: "
									+ recipe.getItemId());
				}
			}
			// Add goals
			for (Entry<Class<? extends GoalProcessor>, Integer> entry : data.goals
					.entrySet()) {
				Goal g = new Goal();
				g.setModulator(entry.getValue());
				g.setProcessor(entry.getKey());
				g.setRecipeId(recipe.getId());
				if (!goalMapper.insert(g)) {
					Gdx.app.error("ERROR", "Unable to add goal: " + g);
				}
			}
			return recipe;
		} else {
			return null;
		}
	}

	private EventBus bus() {
		return SharedInjector.get().getInstance(EventBus.class);
	}

	@Override
	public boolean learnRecipe(Player player, ItemId id) {
		Recipe recipe = recipeMapper.findByItemId(id);
		if (recipe != null
				&& learnedRecipeMapper.findByRecipeId(recipe.getId(),
						player.getId()) == null) {
			LearnedRecipe learned = new LearnedRecipe(recipe.getId(),
					player.getId());
			return learnedRecipeMapper.insert(learned);
		} else {
			return false;
		}
	}

	@Override
	public boolean learnRecipe(ItemId id) {
		return learnRecipe(Player.getCurrent(), id);
	}
	
	@Override
	public ItemBag findIngredients(Item item) {
		ItemBag bag = new ItemBag();
		Recipe recipe = recipeMapper.findByItemId(item.getId());
		if (recipe != null) {
			Collection<Ingredient> ingredients = ingredientMapper.findByRecipeId(recipe.getId());
			for (Ingredient ingredient : ingredients) {
				Item ingredientItem = itemMapper.findById(ingredient.getItemId());
				bag.add(ingredientItem, ingredient.getAmount());
			}
		}
		return bag;		
	}

	@Override
	public Recipe findRecipe(ItemId itemId) {
		return recipeMapper.findByItemId(itemId);
	}

	@Override
	public int getItemAmount(Item item) {
		OwnedItem owned = ownedItemMapper.findById(item.getId(), Player.getCurrent().getId());
		if (owned != null) {
			return owned.getAmount();
		} else {
			return 0;
		}
	}

	@Override
	public Progress getProgress(Profession profession) {
		return progressMapper.findProgress(Player.getCurrent().getId(), profession);
	}

	@Override
	public Progress addXp(Profession profession, int xp) {
		Progress progress = getProgress(profession);
		progress.addXp(xp);
		if (progressMapper.update(progress)) {
			bus().fireEvent(new ProgressEvent(progress));
		}
		return progress;
	}

	@Override
	public Progress setXp(Profession profession, int xp) {
		Progress progress = getProgress(profession);
		progress.setXp(xp);
		if (progressMapper.update(progress)) {
			bus().fireEvent(new ProgressEvent(progress));
		}
		return progress;
	}
}