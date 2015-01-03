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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.bitbrain.craft.Styles;
import de.bitbrain.craft.graphics.UIRenderer;
import de.bitbrain.craft.graphics.UIRenderer.UIMode;
import de.bitbrain.craft.inject.StateScoped;

@StateScoped
public class Overlay {

	private UIRenderer renderer;
	
	public void setRenderer(UIRenderer renderer) {
		this.renderer = renderer;
	}
	
	public void show(Actor ... actors) {
		if (renderer != null) {
			Stage stage = renderer.getOverlay();
			stage.clear();
			for (Actor actor : actors) {
				stage.addActor(actor);
			}
			renderer.setMode(UIMode.OVERLAY);
		}
	}
	
	public void hide() {
		if (renderer != null) {
			renderer.setMode(UIMode.NORMAL);
		}
	}
}
