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

package de.bitbrain.craft.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.controls.IngameControls;
import de.bitbrain.craft.models.Profession;

/**
 * Displays the main game
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class IngameScreen extends AbstractScreen {

	public IngameScreen(Profession profession, CraftGame game) {
		super(game);
	}

	@Override
	protected void onCreateStage(Stage stage) {

	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
	}

	@Override
	protected Stage createStage(int width, int height, Batch batch) {
		return new IngameControls(this, new ScreenViewport(), batch);
	}

	@Override
	protected void onDraw(Batch batch, float delta) {
	}

	@Override
	protected void onShow() {

	}

}
