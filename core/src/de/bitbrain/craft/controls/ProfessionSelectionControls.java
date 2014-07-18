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

package de.bitbrain.craft.controls;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.screens.ProfessionScreen;
import de.bitbrain.craft.screens.TitleScreen;

/**
 * Controls for {@see de.bitbrain.craft.screens.ProfessionScreen}
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ProfessionSelectionControls extends Stage {
	
	private ProfessionScreen screen;
	
	public ProfessionSelectionControls(ProfessionScreen screen, Viewport viewport, Batch batch) {
		super(viewport, batch);
		this.screen = screen;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keyCode) {
		
		if (keyCode == Keys.ESCAPE) {
			Sound sound = SharedAssetManager.get(Assets.SOUND_ABORT, Sound.class);
			sound.play(1.0f, 0.8f, 1.0f);
			screen.setScreen(new TitleScreen(screen.getGame()));
			return true;
		}
		
		return super.keyDown(keyCode);
	}
	
	
}
