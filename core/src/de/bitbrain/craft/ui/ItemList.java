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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.engio.mbassy.listener.Handler;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.google.inject.Inject;

import de.bitbrain.craft.animations.ActorTween;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.ItemBag;
import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.ItemEvent;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.ui.widgets.ItemWidget;
import de.bitbrain.craft.util.ItemComparator;

/**
 * Connects element info to the database
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class ItemList {

	private final WidgetGroup group;

	private final Map<ItemId, ItemWidget> widgets;

	private final Map<Actor, Item> items;

	private ItemWidgetComparator comparator;

	@Inject
	private EventBus eventBus;

	@Inject
	private API api;

	@Inject
	private TweenManager tweenManager;

	public ItemList(WidgetGroup group) {
		SharedInjector.get().injectMembers(this);
		this.group = group;
		widgets = new HashMap<ItemId, ItemWidget>();
		items = new HashMap<Actor, Item>();
		comparator = new ItemWidgetComparator();
		eventBus.subscribe(this);
		ItemBag itemBag = api.getOwnedItems(Player.getCurrent().getId());
		for (Entry<Item, Integer> entry : itemBag) {
			addElements(entry.getKey(), entry.getValue());
		}
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

	private void removeElements(Item item, int amount) {
		final ItemWidget widget = widgets.get(item.getId());
		if (widget.getAmount() == Item.INFINITE_AMOUNT) {
			return;
		}
		int newAmount = widget.getAmount() - amount;
		widget.setAmount(item, newAmount);
		if (newAmount <= 0 && !api.canCraft(item.getId())) {
			widgets.remove(item.getId());
			items.remove(widget);
			Tween.to(widget, ActorTween.ALPHA, 0.65f).target(0f)
					.ease(TweenEquations.easeOutQuad)
					.setCallbackTriggers(TweenCallback.COMPLETE)
					.setCallback(new TweenCallback() {
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							group.removeActor(widget);
						}
					}).start(tweenManager);
			Gdx.app.log("INFO", "Removed element with id='" + item.getId()
					+ "' from " + group);
		}

		group.getChildren().begin();
		group.getChildren().sort(comparator);
		group.getChildren().end();
	}

	private void addElements(Item item, int amount) {
		if (!widgets.containsKey(item.getId())) {
			ItemWidget panel = new ItemWidget(item, amount);
			widgets.put(item.getId(), panel);
			group.addActor(panel);
			items.put(panel, item);
			Gdx.app.log("INFO", "Attached element with id='" + item.getId()
					+ "' to " + group);
		} else {
			ItemWidget panel = widgets.get(item.getId());
			tweenManager.killTarget(panel);
			panel.setAmount(item, panel.getAmount() + amount);
			Gdx.app.log("INFO", "Updated element with id='" + item.getId()
					+ "' in " + group);
		}
		group.getChildren().begin();
		group.getChildren().sort(comparator);
		group.getChildren().end();
	}

	private class ItemWidgetComparator implements Comparator<Actor> {

		private ItemComparator itemComparator = new ItemComparator();

		@Override
		public int compare(Actor actorA, Actor actorB) {
			Item itemA = items.get(actorA);
			Item itemB = items.get(actorB);
			return itemComparator.compare(itemA, itemB);
		}

	}
}
