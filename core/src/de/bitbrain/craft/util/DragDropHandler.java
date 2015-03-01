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

package de.bitbrain.craft.util;

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
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.google.inject.Inject;

import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.audio.SoundManager;
import de.bitbrain.craft.audio.SoundType;
import de.bitbrain.craft.audio.SoundUtils;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.ItemId;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.ItemEvent;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.graphics.IconManager;
import de.bitbrain.craft.graphics.IconManager.IconDrawable;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.tweens.VectorTween;
import de.bitbrain.craft.ui.widgets.TabWidget.Tab;

/**
 * Handler which handles drag and drop. This handler is capable of handling
 * multiple drag&drops. It reacts to any mouse and element events.
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class DragDropHandler {

	// Determines if enabled or not
	private boolean enabled;

	// Contains all metadata for icons
	private Map<ItemId, IconMetadata> metadata;

	// Temporary direction variable for target
	private Vector2 target;

	@Inject
	private EventBus eventBus;

	@Inject
	private SoundManager soundManager;

	@Inject
	private API api;

	@Inject
	private TweenManager tweenManager;

	@Inject
	private IconManager iconManager;

	@PostConstruct
	public void init() {
		metadata = new HashMap<ItemId, IconMetadata>();
		target = new Vector2();
		enabled = true;
		eventBus.subscribe(this);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void draw(Batch batch, float delta) {
		if (enabled) {
			for (Entry<ItemId, IconMetadata> entry : metadata.entrySet()) {
				IconMetadata data = entry.getValue();
				Vector2 location = data.location;
				Vector2 size = data.size;
				target.x = Sizes.worldMouseX() / Sizes.worldScreenFactorX();
				target.y = getScreenY();
				float speed = 0f;

				if (data.drop) {
					target.x = data.source.x;
					target.y = data.source.y;
					speed = 3f;
					// Check if near, then drop everything
					if (target.cpy().sub(location).len() < Sizes.dragIconSize()) {
						remove(entry.getKey());
						break;
					}
				}

				if (speed <= 0) {
					// Apply direct mouse position
					location.x = target.x;
					location.y = target.y;
				} else {
					// Move the location towards the mouse
					location.x += (target.x - location.x) * delta * speed;
					location.y += (target.y - location.y) * delta * speed;
				}

				IconDrawable icon = data.drawable;

				for (int i = 0; i < data.amount; ++i) {
					icon.x = location.x + size.x / 2f + 10.25f
							* (float) Math.cos(i * 30f);
					icon.y = location.y - size.y / 2f + 10.25f
							* (float) Math.sin(i * 30f);
					icon.rotation = 180f;
					icon.width = size.x * -Sizes.worldScreenFactorX();
					icon.height = size.y * Sizes.worldScreenFactorY();
					icon.draw(batch, 1f);
				}
			}
		}
	}

	public void clear() {
		metadata.clear();
	}

	@Handler
	public void onEvent(ItemEvent event) {
		// ON ITEM REMOVE: Remove it from this handler
		if (event.getType().equals(EventType.REMOVE)) {
			remove(event.getModel().getId());
		}
	}

	@Handler
	public void onEvent(MouseEvent<?> event) {
		if (event.getModel() instanceof Item) {
			final Item item = (Item) event.getModel();

			if (event.getType() == EventType.MOUSEDRAG) {
				int size = 1;
				if (event.getParam(0) != null) {
					size = (Integer) event.getParam(0);
				}
				add(item, size);
				SoundUtils.playItemSound(item, SoundType.DRAG, soundManager,
						api);
			} else if (event.getType() == EventType.MOUSEDROP) {
				IconMetadata data = metadata.get(item.getId());
				data.drop = true;
				tweenManager.killTarget(data.size);
				animateVector(data.size, 1.7f, 0f, new TweenCallback() {
					@Override
					public void onEvent(int type, BaseTween<?> source) {
					} // do nothing
				});
				SoundUtils.playItemSound(item, SoundType.DROP, soundManager,
						api);
			}
		} else if (event.getModel() instanceof Tab) {
			clear();
		}
	}

	private float getScreenY() {
		return (Sizes.worldHeight() / Sizes.worldScreenFactorY())
				- (Sizes.worldMouseY() / Sizes.worldScreenFactorY())
				+ (Gdx.graphics.getHeight() / 8f) * Sizes.worldScreenFactorY();
	}

	private void add(final Item item, int amount) {
		final IconMetadata data = new IconMetadata();
		data.drawable = iconManager.fetch(item.getIcon());
		data.location = new Vector2(Sizes.worldMouseX(), getScreenY());
		data.drop = false;
		data.source = new Vector2(Sizes.worldMouseX(), getScreenY());
		data.size = new Vector2();
		data.amount = amount;
		animateVector(data.size, 1f, Sizes.dragIconSize(), new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				animateDragging(data.size);
			}
		});
		metadata.put(item.getId(), data);
	}

	private void remove(ItemId id) {
		if (metadata.containsKey(id)) {
			IconMetadata data = metadata.get(id);
			tweenManager.killTarget(data.size);
			metadata.remove(id);
		}
	}

	private void animateVector(Vector2 vec, float time, float target,
			TweenCallback callback) {
		Tween.to(vec, VectorTween.X, time).target(target)
				.ease(TweenEquations.easeOutQuart).start(tweenManager);
		Tween.to(vec, VectorTween.Y, time).target(target)
				.ease(TweenEquations.easeOutQuart).setCallback(callback)
				.setCallbackTriggers(TweenCallback.COMPLETE)
				.start(tweenManager);
	}

	private void animateDragging(Vector2 vec) {
		Tween.to(vec, VectorTween.X, 1.0f)
				.target(Sizes.dragIconSize() + Sizes.dragIconSize() / 3.2f)
				.repeatYoyo(Tween.INFINITY, 0f)
				.ease(TweenEquations.easeOutBack).start(tweenManager);
		Tween.to(vec, VectorTween.Y, 1.0f)
				.target(Sizes.dragIconSize() + Sizes.dragIconSize() / 3.2f)
				.repeatYoyo(Tween.INFINITY, 0f)
				.ease(TweenEquations.easeOutBack).start(tweenManager);
	}

	private class IconMetadata {

		public IconDrawable drawable;

		public Vector2 location, source, size;

		public Boolean drop;

		public int amount;
	}
}