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

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.audio.SoundUtils;
import de.bitbrain.craft.events.KeyEvent;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.ui.ProfessionSelectionView;
import de.bitbrain.craft.ui.ProfessionSelectionView.ProfessionSelectListener;
import de.bitbrain.craft.util.DirectPlayerDataProvider;

/**
 * Shows up a selection for available professions
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ProfessionScreen extends AbstractScreen implements ProfessionSelectListener {
	
	private ProfessionSelectionView selection;

	@Override
	protected void onCreateStage(Stage stage) {
		// TODO: Fix player ID here
		selection = new ProfessionSelectionView(new DirectPlayerDataProvider(1));		
		selection.addProfessionSelectListener(this);
		selection.align(Align.center);
		stage.addActor(selection);
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		selection.setWidth(Sizes.worldWidth());
		selection.setHeight(Sizes.worldHeight());
	}

	@Override
	protected void onDraw(Batch batch, float delta) {
	}

	@Override
	protected void onShow() {

	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.ui.ProfessionSelection.ProfessionSelectListener#onSelect(de.bitbrain.craft.models.Profession)
	 */
	@Override
	public void onSelect(Profession profession) {
		setScreen(IngameScreen.class);
	}
	
	@Override
	protected Viewport createViewport() {
		return new FillViewport(Sizes.worldWidth(), Sizes.worldHeight());
	}
	
	@Handler
	void onEvent(KeyEvent event) {
		if (event.getKey() == Keys.ESCAPE || event.getKey() == Keys.BACK) {
			setScreen(TitleScreen.class);
			SoundUtils.play(Assets.SND_ABORT, 1.0f, 0.7f);
		}
	}
}