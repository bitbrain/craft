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
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import de.bitbrain.craft.events.ElementMessage;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Recipe;
import de.bitbrain.craft.ui.ElementInfoPanel.ElementData;

/** Connects element info to the database
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ElementInfoConnector {
	
	private final WidgetGroup group;
	
	private final Class<?> elementClass;
	
	private final Map<String, ElementInfoPanel> elements;
	
	private final Map<String, ElementData> dataMap;
	
	public ElementInfoConnector(WidgetGroup group, Class<?> elementClass) {
		this.group = group;
		this.elementClass = elementClass;
		elements = new HashMap<String, ElementInfoPanel>();
		dataMap = new HashMap<String, ElementData>();
	}
	
	@Handler
	public void onEvent(ElementMessage<?> message) {
		// Only do something if message fit to this connector type
		if (message.getModel().getClass().equals(elementClass)) {
			switch (message.getType()) {
			case ADD:
				addElements(message.getModel().getId(), message.getModel(), message.getAmount());
				break;
			case REMOVE:
				removeElements(message.getModel().getId(), message.getModel(), message.getAmount());
				break;
			case UPDATE:
				break;
			default:
				break;
			}
		}
	}
	
	private void removeElements(String id, Object model, int amount) {

		ElementData data = dataMap.get(id);
		
		if (data != null) {	
			int newAmount = data.getAmount() - amount;
			if (newAmount < 1) {
				elements.remove(id);
				dataMap.remove(id);
			} else {
				ElementInfoPanel panel = elements.get(id);
				panel.setAmount(newAmount);
			}
			Gdx.app.log("INFO", "Removed element with id='" + id + "' from " + group);
		}
	}
	
	private void addElements(String id, Object model, int amount) {
		ElementData data = null;
		if (model instanceof Item) {
			data = new ItemElementAdapter((Item)model, amount);
		} else if (model instanceof Recipe) {
			data = new RecipeElementAdapter((Recipe)model);
		} else {
			throw new RuntimeException(model + " can't be converted into a valid actor.");
		}
		
		dataMap.put(id, data);
		
		if (!elements.containsKey(id)) {
			ElementInfoPanel panel = new ElementInfoPanel(data);
			elements.put(id, panel);
			group.addActor(panel);
			Gdx.app.log("INFO", "Attached element with id='" + id + "' to " + group);
		} else {
			ElementInfoPanel panel = elements.get(id);
			panel.setData(data);
			Gdx.app.log("INFO", "Updated element with id='" + id + "' in " + group);
		}
	}
}
