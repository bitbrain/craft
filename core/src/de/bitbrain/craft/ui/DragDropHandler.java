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
import com.google.inject.Inject;

import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.SharedInjector;

/**
 * Handler which handles drag and drop
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class DragDropHandler {
	
	private boolean enabled;
	
	@Inject
	private EventBus eventBus;
	
	public DragDropHandler() {
		SharedInjector.get().injectMembers(this);
		eventBus.subscribe(this);
		enabled = true;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void draw(Batch batch, float delta) {
		if (enabled) {
			
		}
	}
	
	@Handler
	public void onEvent(MouseEvent<?> message) {
		if (message.getModel() instanceof ElementInfoPanel) {
			ElementInfoPanel panel = (ElementInfoPanel) message.getModel();
			System.out.println(message.getType() + " on " + panel.getData().getId() + " at " + message.getMouseX() + "|" + message.getMouseY());
		}
	}
}