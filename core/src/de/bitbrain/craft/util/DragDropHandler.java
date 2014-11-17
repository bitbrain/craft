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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.google.inject.Inject;

import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.core.IconManager.IconDrawable;
import de.bitbrain.craft.events.ElementEvent;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.tweens.VectorTween;
import de.bitbrain.craft.ui.elements.ElementData;
import de.bitbrain.craft.ui.widgets.TabWidget;
import de.bitbrain.craft.ui.widgets.TabWidget.Tab;

/**
 * Handler which handles drag and drop. This handler is capable of
 * handling multiple drag&drops. It reacts to any mouse and element events.
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class DragDropHandler {
	
	// Determines if enabled or not
	private boolean enabled;
	
	// Contains all icons to draw
	private Map<String, IconDrawable> icons;
	
	// Contains all current locations and their sources
	private Map<String, Vector2> locations, sources, sizes;
	
	// Contains values to determine if an item has been dropped
	private Map<String, Boolean> drops;
	
	// Temporary direction variable for target
	private Vector2 target;
	
	@Inject
	private EventBus eventBus;
	
	@Inject
	private TweenManager tweenManager;
	
	@PostConstruct
	public void init() {
		icons = new HashMap<String, IconDrawable>();
		locations = new HashMap<String, Vector2>();
		sources = new HashMap<String, Vector2>();
		drops = new HashMap<String, Boolean>();
		sizes = new HashMap<String, Vector2>();
		target = new Vector2();
		enabled = true;
		eventBus.subscribe(this);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void draw(Batch batch, float delta) {
		if (enabled) {
			for (Entry<String, IconDrawable> entry : icons.entrySet()) {
				Vector2 location = locations.get(entry.getKey());
				Vector2 size = sizes.get(entry.getKey());
				target.x = Sizes.worldMouseX();
				target.y = getScreenY();
				float speed = 15f;

				if (drops.get(entry.getKey())) {
					target.x = sources.get(entry.getKey()).x;
					target.y = sources.get(entry.getKey()).y;
					speed = 3f;
					// Check if near, then drop everything
					if (target.cpy().sub(location).len() <  Sizes.dragIconSize()) {
						remove(entry.getKey());
						break;
					}
				}
				
				// Move the location towards the mouse
				location.x += (target.x - location.x) * delta * speed;
				location.y += (target.y - location.y) * delta * speed; 
				
				// Apply location
				IconDrawable icon = entry.getValue();
				icon.x = location.x - size.x / 2f;
				icon.y = location.y - size.y / 2f;
				icon.rotation = 0f;
				icon.width = size.x * Sizes.worldScreenFactorX();
				icon.height = size.y* Sizes.worldScreenFactorY();
				icon.draw(batch, 1f);
			}
		}
	}
	
	public void clear() {
		icons.clear();
		drops.clear();
		locations.clear();
		sources.clear();
	}
	
	@Handler
	public void onEvent(ElementEvent<?> event) {
		// ON ITEM REMOVE: Remove it from this handler
		if (event.getType().equals(EventType.REMOVE)) {
			remove(event.getModel().getId());
		}
	}
	
	@Handler
	public void onEvent(MouseEvent<?> event) {
		if (event.getModel() instanceof ElementData) {			
			final ElementData data = (ElementData) event.getModel();
			
			if (event.getType() == EventType.MOUSEDRAG) {
				add(data);
			} else if (event.getType() == EventType.MOUSEDROP) {
				String id = data.getId();
				drops.put(id, true);
				tweenManager.killTarget(sizes.get(id));
				animateVector(sizes.get(id), 1.7f, 0f, new TweenCallback() {
					@Override 
					public void onEvent(int type, BaseTween<?> source) {} // do nothing
				});
			}
		} else if (event.getModel() instanceof Tab) {
			clear();
		}
	}
	
	private float getScreenY() {
		return Sizes.worldMouseY();
	}
	
	private void add(final ElementData data) {
		icons.put(data.getId(), data.getIcon());
		locations.put(data.getId(), new Vector2(Sizes.worldMouseX(), getScreenY()));
		drops.put(data.getId(), false);
		sources.put(data.getId(), new Vector2(Sizes.worldMouseX(), getScreenY()));
		sizes.put(data.getId(), new Vector2());
		animateVector(sizes.get(data.getId()), 1f,  Sizes.dragIconSize(), new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				animateDragging(sizes.get(data.getId()));
			}			
		});
	}
	
	private void remove(String id) {
		if (icons.containsKey(id)) {
			icons.remove(id);
			locations.remove(id);
			drops.remove(id);
			sources.remove(id);
			tweenManager.killTarget(sizes.get(id));
			sizes.remove(id);
		}
	}
	
	private void animateVector(Vector2 vec, float time, float target, TweenCallback callback) {
		Tween.to(vec, VectorTween.X, time)
			 .target(target)
			 .ease(TweenEquations.easeOutQuart)
			 .start(tweenManager);
		Tween.to(vec, VectorTween.Y, time)
			 .target(target)
			 .ease(TweenEquations.easeOutQuart)
			 .setCallback(callback)
			 .setCallbackTriggers(TweenCallback.COMPLETE)
			 .start(tweenManager);
	}
	
	private void animateDragging(Vector2 vec) {
		Tween.to(vec, VectorTween.X, 1.0f)
			 .target( Sizes.dragIconSize() +  Sizes.dragIconSize() / 3.2f)
			 .repeatYoyo(Tween.INFINITY, 0f)
			 .ease(TweenEquations.easeOutBack)
			 .start(tweenManager);
		Tween.to(vec, VectorTween.Y, 1.0f)
			 .target( Sizes.dragIconSize() +  Sizes.dragIconSize() / 3.2f)
			 .repeatYoyo(Tween.INFINITY, 0f)
			 .ease(TweenEquations.easeOutBack)
			 .start(tweenManager);
	}
}