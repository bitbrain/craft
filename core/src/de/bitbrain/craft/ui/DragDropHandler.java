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
import java.util.Map.Entry;

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.google.inject.Inject;

import de.bitbrain.craft.core.IconManager.Icon;
import de.bitbrain.craft.events.ElementEvent;
import de.bitbrain.craft.events.Event.MessageType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.ui.ElementInfoPanel.ElementData;
import de.bitbrain.craft.ui.TabPanel.TabControl;

/**
 * Handler which handles drag and drop. This handler is capable of
 * handling multiple drag&drops. It reacts to any mouse and element events.
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class DragDropHandler {
	
	// Default icon size
	private static final float ICON_SIZE = 64f;
	
	// Determines if enabled or not
	private boolean enabled;
	
	// Contains all icons to draw
	private final Map<String, Icon> icons;
	
	// Contains all current locations and their sources
	private final Map<String, Vector2> locations, sources;
	
	// Contains values to determine if an item has been dropped
	private final Map<String, Boolean> drops;
	
	// Temporary direction variable for target
	private Vector2 target;
	
	@Inject
	private EventBus eventBus;

	public DragDropHandler() {
		SharedInjector.get().injectMembers(this);
		icons = new HashMap<String, Icon>();
		locations = new HashMap<String, Vector2>();
		sources = new HashMap<String, Vector2>();
		drops = new HashMap<String, Boolean>();
		target = new Vector2();
		eventBus.subscribe(this);
		enabled = true;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void draw(Batch batch, float delta) {
		if (enabled) {
			for (Entry<String, Icon> entry : icons.entrySet()) {
				Vector2 location = locations.get(entry.getKey());
				
				target.x = Gdx.input.getX();
				target.y = getScreenY();
				float speed = 10f;

				if (drops.get(entry.getKey())) {
					target.x = sources.get(entry.getKey()).x;
					target.y = sources.get(entry.getKey()).y;
					speed = 6f;
					// Check if near, then drop everything
					if (target.cpy().sub(location).len() < ICON_SIZE / 2f) {
						remove(entry.getKey());
						break;
					}
				}
				
				// Move the location towards the mouse
				location.x += (target.x - location.x) * delta * speed;
				location.y += (target.y - location.y) * delta * speed; 
				
				// Apply location
				Icon icon = entry.getValue();
				icon.x = location.x - ICON_SIZE / 2f;
				icon.y = location.y - ICON_SIZE / 2f;
				icon.rotation = 0f;
				icon.width = ICON_SIZE;
				icon.height = ICON_SIZE;
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
	}
	
	@Handler
	public void onEvent(MouseEvent<?> event) {
		if (event.getModel() instanceof ElementInfoPanel) {			
			ElementInfoPanel panel = (ElementInfoPanel) event.getModel();
			
			if (event.getType() == MessageType.MOUSEDRAG) {
				add(panel);
			} else if (event.getType() == MessageType.MOUSEDROP) {
				drops.put(panel.getData().getId(), true);
			}
		} else if (event.getModel() instanceof TabControl) {
			clear();
		}
	}
	
	private float getScreenY() {
		return Gdx.input.getY();
	}
	
	private void add(ElementInfoPanel panel) {
		ElementData data = panel.getData();
		icons.put(data.getId(), data.getIcon());
		locations.put(data.getId(), new Vector2(Gdx.input.getX(), getScreenY()));
		drops.put(data.getId(), false);
		sources.put(data.getId(), new Vector2(Gdx.input.getX(), getScreenY()));
	}
	
	private void remove(String id) {
		icons.remove(id);
		locations.remove(id);
		drops.remove(id);
		sources.remove(id);
	}
}