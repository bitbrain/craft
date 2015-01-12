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

package de.bitbrain.craft.ui.elements;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.SnapshotArray;
import com.google.inject.Inject;

import de.bitbrain.craft.events.ElementEvent;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.ui.widgets.ElementWidget;

/** Connects element info to the database
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class ElementConnector<T> {
	
	private final static float SPACING = 10f;
	
	private final WidgetGroup group;
	
	private final Class<?> elementClass;
	
	private final Map<String, ElementWidget> elements;
	
	private final Map<String, Actor> spacings;
	
	private final Map<Actor, T> values;
	
	private final Map<String, ElementData> dataMap;
	
	@Inject
	private EventBus eventBus;
	
	private ElementDataProvider<T> provider;
	
	private Comparator<T> comparator = new Comparator<T>() {
		@Override
		public int compare(T o1, T o2) {
			return 0;
		}		
	};
	
	public ElementConnector(WidgetGroup group, ElementDataProvider<T> provider, Class<T> elementClass) {
		SharedInjector.get().injectMembers(this);
		this.group = group;
		this.provider = provider;
		this.elementClass = elementClass;
		elements = new HashMap<String, ElementWidget>();
		dataMap = new HashMap<String, ElementData>();
		spacings = new HashMap<String, Actor>();
		values = new HashMap<Actor, T>();
		eventBus.subscribe(this);
	}
	
	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}
	
	@SuppressWarnings("unchecked")
	@Handler
	public void onEvent(ElementEvent<?> message) {
		// Only do something if message fit to this connector type
		if (message.getModel().getClass().equals(elementClass)) {
			switch (message.getType()) {
			case ADD:
				addElements(message.getModel().getId(), (T) message.getModel(), message.getAmount());
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
	
	public void dispose() {
		elements.clear();
		dataMap.clear();
		group.clear();
	}
	
	private Actor addSpacing(String id) {
		Actor spacing = new Actor();
		spacing.setWidth(group.getWidth());
		spacing.setHeight(SPACING);
		spacings.put(id, spacing);
		return spacing;
	}
	
	private void removeElements(String id, Object model, int amount) {

		ElementData data = dataMap.get(id);
		if (data != null) {	
			int newAmount = data.getAmount() - amount;
			if (newAmount >= 0) {
				ElementWidget panel = elements.get(id);
				panel.setAmount(newAmount);
				Gdx.app.log("INFO", "Removed element with id='" + id + "' from " + group);
			}
		}
	}
	
	private void addElements(String id, T model, int amount) {
		ElementData data = null;
		if (dataMap.containsKey(id)) {
			data = dataMap.get(id);
			data.setAmount(data.getAmount() + amount);
		} else {
			data = provider.create(model, amount);
			dataMap.put(id, data);
		}
		
		if (!elements.containsKey(id)) {
			ElementWidget panel = new ElementWidget(data);
			elements.put(id, panel);
			group.addActor(addSpacing(id));
			group.addActor(panel);
			values.put(panel, model);
			Gdx.app.log("INFO", "Attached element with id='" + id + "' to " + group);
		} else {
			ElementWidget panel = elements.get(id);
			panel.setData(data);
			Gdx.app.log("INFO", "Updated element with id='" + id + "' in " + group);
		}
		sortAndUpdate();
	}
	
	private void sortAndUpdate() {
		SnapshotArray<Actor> actors = group.getChildren();
		for (Actor actorA : actors) {
			for (Actor actorB : actors) {
				T elemA = values.get(actorA);
				T elemB = values.get(actorB);
				if (elemA != null && elemB != null) {
					int value = comparator.compare(elemA, elemB);
					if (value > 0) {
						group.removeActor(actorA);
						group.addActorAfter(actorB, actorA);
					} else if (value < 0) {
						group.removeActor(actorA);
						group.addActorBefore(actorB, actorA);
					}
				}
			}
		}
	}
	
	public interface ElementDataProvider<T> {
		ElementData create(T model, int amount);
	}
}
