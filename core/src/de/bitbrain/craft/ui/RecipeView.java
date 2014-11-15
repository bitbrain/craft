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

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.google.inject.Inject;

import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.PostConstruct;

/**
 * Provides the view of a single recipe. This view is blocked by default. If a
 * recipe has been clicked, it will open instantly with further information.
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class RecipeView extends VerticalGroup {

	@Inject
	private TabView tabPanel;
	
	@Inject
	private EventBus eventBus;
	
	@Inject
	private API api;
	
	private ElementData data;
	
	private VerticalGroup content;
	
	@PostConstruct
	public void init() {
		content = new VerticalGroup();
		addActor(content);
		eventBus.subscribe(this);
		align(Align.left);
		fill().pad(10f);
		content.fill().pad(10f);
	}
	
	@Handler
	public void onEvent(MouseEvent<?> event) {
		if (event.getModel() instanceof ElementData && event.getType() == EventType.CLICK) {
			ElementData tmpData = (ElementData) event.getModel();
			if (api.isItemId(tmpData.getId())) {
				tabPanel.setTab(Tabs.RECIPE);
			}
			if (data == null || !isModified()) {
				data = tmpData.copy();
				content.clear();
				content.addActor(generateTop(data));
			}
		}
	}
	
	private boolean isModified() {
		return false;
	}
	
	private Actor generateTop(ElementData data) {
		HorizontalGroup group = new HorizontalGroup();
		group.align(Align.left);
		data.setAmount(-1);
		ElementIcon icon = new ElementIcon(data);
		group.addActor(icon);
		HorizontalGroup wrapper = new HorizontalGroup();
		Label caption = new Label(data.getName(), Styles.LBL_ITEM);
		caption.setColor(data.getRarity().getColor());	
		icon.setWidth(caption.getHeight() * 4);
		icon.setHeight(caption.getHeight() * 4);
		wrapper.addActor(caption);
		wrapper.padLeft(15f);
		group.addActor(wrapper);
		return group;
	}
}