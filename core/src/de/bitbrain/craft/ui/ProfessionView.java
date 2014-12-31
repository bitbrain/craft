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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.professions.ProfessionLogic;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.ui.elements.ElementData;

/**
 * General view component for professions
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ProfessionView extends Actor {
	
	private ProfessionLogic professionLogic;
	
	@Inject
	private EventBus eventBus;
	
	@Inject
	private API api;
	
	public ProfessionView(ProfessionLogic professionLogic) {
		SharedInjector.get().injectMembers(this);
		eventBus.subscribe(this);
		this.professionLogic = professionLogic;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		//Texture texture = SharedAssetManager.get(Assets.TEX_BUTTON_GREEN, Texture.class);
		//batch.draw(texture, getX(), getY(), getWidth(),	getHeight());
	}
	
	@Handler
	public void onEvent(MouseEvent<?> event) {
		if (event.getType().equals(EventType.MOUSEDROP) &&
			event.getModel() instanceof ElementData) {
			ElementData data = (ElementData) event.getModel();
			if (api.isValidItem(data.getId())) {
				Item item = api.getItem(data.getId());
				if (professionLogic.add(item)) {
					// Item accepted, remove it from system
					api.removeItem(Player.getCurrent().getId(), item.getId(), 1);
				}
			}
		}
	}
}
