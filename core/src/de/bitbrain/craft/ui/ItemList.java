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

package de.bitbrain.craft.ui;

import java.util.HashMap;
import java.util.Map;

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.google.inject.Inject;

import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.ItemEvent;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.ui.widgets.ItemWidget;

/**
 * Connects element info to the database
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class ItemList {

	private final static float SPACING = 10f;

	private final WidgetGroup group;

	private final Map<ItemId, ItemWidget> widgets;

	private final Map<ItemId, Actor> spacings;

	private final Map<Actor, Item> values;

	@Inject
	private EventBus eventBus;

	public ItemList(WidgetGroup group) {
		SharedInjector.get().injectMembers(this);
		this.group = group;
		widgets = new HashMap<ItemId, ItemWidget>();
		spacings = new HashMap<ItemId, Actor>();
		values = new HashMap<Actor, Item>();
		eventBus.subscribe(this);
	}

	@Handler
	public void onEvent(ItemEvent message) {
		switch (message.getType()) {
		case ADD:
			addElements(message.getModel(), message.getAmount());
			break;
		case REMOVE:
			removeElements(message.getModel(), message.getAmount());
			break;
		case UPDATE:
			break;
		default:
			break;
		}

	}

	public void dispose() {
		widgets.clear();
		group.clear();
	}

	private Actor addSpacing(ItemId id) {
		Actor spacing = new Actor();
		spacing.setWidth(group.getWidth());
		spacing.setHeight(SPACING);
		spacings.put(id, spacing);
		return spacing;
	}

	private void removeElements(Item item, int amount) {
		ItemWidget widget = widgets.get(item.getId());
		int newAmount = widget.getAmount() - amount;
		if (newAmount >= 0) {
			widget.setAmount(item, newAmount);
			Gdx.app.log("INFO", "Removed element with id='" + item.getId()
					+ "' from " + group);
		}

	}

	private void addElements(Item item, int amount) {
		if (!widgets.containsKey(item.getId())) {
			ItemWidget panel = new ItemWidget(item, amount);
			widgets.put(item.getId(), panel);
			group.addActor(addSpacing(item.getId()));
			group.addActor(panel);
			values.put(panel, item);
			Gdx.app.log("INFO", "Attached element with id='" + item.getId()
					+ "' to " + group);
		} else {
			ItemWidget panel = widgets.get(item.getId());
			panel.setAmount(item, amount);
			Gdx.app.log("INFO", "Updated element with id='" + item.getId()
					+ "' in " + group);
		}
	}
}
