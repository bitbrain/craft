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

import javax.annotation.PostConstruct;

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.google.inject.Inject;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;

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
	private TabPanel tabPanel;
	
	@Inject
	private EventBus eventBus;
	
	@Inject
	private API api;
	
	@PostConstruct
	public void init() {
		eventBus.subscribe(this);
	}
	
	@Handler
	public void onEvent(MouseEvent<?> event) {
		if (event.getModel() instanceof ElementData && event.getType() == EventType.CLICK) {
			ElementData data = (ElementData) event.getModel();
			if (api.isRecipeId(data.getId())) {
				tabPanel.setTab(Tabs.RECIPE);
			}
		}
	}
}