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
package de.bitbrain.craft.ui.widgets;

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.google.inject.Inject;

import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.ItemBag;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.ui.Tabs;

/**
 * Provides the view of a single recipe. This view is blocked by default. If a
 * recipe has been clicked, it will open instantly with further information.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class RecipeWidget extends Table {

	@Inject
	private TabWidget tabPanel;
	
	@Inject
	private EventBus eventBus;
	
	@Inject
	private API api;
	
	@PostConstruct
	public void init() {
		debug();
		eventBus.subscribe(this);
	}
	
	@Handler
	public void onEvent(MouseEvent<?> event) {
		if (event.getModel() instanceof Item && event.getType() == EventType.CLICK) {
			Item item = (Item) event.getModel();
			if (api.canCraft(Player.getCurrent(), item.getId())) {
				tabPanel.setTab(Tabs.CRAFTING);
				if (!isModified()) {
					clear();
					add(generateTop(item)).row();
					String description = Bundles.itemDescriptions.get(item.getId().toString());
					if (!description.isEmpty()) {
						add(generateDescription(item)).fillX().row();
					}
					add(generateMaterials(item));
				}
			}
		}
	}
	
	private boolean isModified() {
		return false;
	}
	
	private Actor generateDescription(Item item) {
		Container<Label> container = new Container<Label>(new Label(Bundles.itemDescriptions.get(item.getId().toString()), Styles.LBL_BROWN));
		container.padTop(20f).padLeft(10f).align(Align.left);
		return container;
	}
	
	private Actor generateTop(Item item) {
		HorizontalGroup group = new HorizontalGroup();
		group.align(Align.left);
		IconWidget icon = new IconWidget(item.getIcon(), -1);
		group.addActor(icon);
		HorizontalGroup wrapper = new HorizontalGroup();
		Label caption = new Label(Bundles.items.get(item.getId().toString()), Styles.LBL_ITEM);
		caption.setColor(item.getRarity().getColor());	
		icon.setWidth(caption.getHeight() * 4);
		icon.setHeight(caption.getHeight() * 4);
		wrapper.addActor(caption);
		wrapper.padLeft(15f);
		group.addActor(wrapper);
		return group;
	}
	
	private Actor generateMaterials(Item item) {
		Table table = new Table();
		ItemBag materials = api.findIngredients(item);
		System.out.println(materials.size());
		return table;
	}
}