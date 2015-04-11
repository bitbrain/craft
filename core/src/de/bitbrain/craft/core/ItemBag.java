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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import de.bitbrain.craft.models.Item;

/**
 * Bag which contains items
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ItemBag implements Iterable<Entry<Item, Integer>> {

	private Map<Item, Integer> items;
	
	private Map<ItemId, Item> itemIds;

	public ItemBag() {
		items = new HashMap<Item, Integer>();
		itemIds = new HashMap<ItemId, Item>();
	}

	public void add(Item item, Integer amount) {
		if (items.containsKey(item)) {
			Integer currentAmount = items.get(item);
			items.put(item,  currentAmount + amount);
		} else {
			items.put(item, amount);
			itemIds.put(item.getId(), item);
		}
	}

	@Override
	public Iterator<Entry<Item, Integer>> iterator() {
		return items.entrySet().iterator();
	}
	
	public int getAmount(Item item) {
		Integer amount = items.get(item);
		return amount != null ? amount : 0;
	}

	public boolean contains(Item item) {
		return items.containsKey(item);
	}

	public int size() {
		return items.size();
	}

	public void clear(ItemId id) {
		Item item = itemIds.get(id);
		if (item != null) {
			items.remove(item);
			itemIds.remove(id);
		}
	}
}
