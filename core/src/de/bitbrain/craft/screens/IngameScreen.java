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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.controls.IngameControls;
import de.bitbrain.craft.core.IconManager;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.ui.TabPanel;

/**
 * Displays the main game
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class IngameScreen extends AbstractScreen {
	
	private IconManager iconManager = IconManager.getInstance();
	
	private TabPanel tabPanel;

	public IngameScreen(Profession profession, CraftGame game) {
		super(game);
	}

	@Override
	protected void onCreateStage(Stage stage) {
		tabPanel = new TabPanel();
		stage.addActor(tabPanel);
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);	

		tabPanel.setWidth(Gdx.graphics.getWidth() / 2.2f);
		tabPanel.setHeight(Gdx.graphics.getHeight() / 1.1f);

		tabPanel.padLeft(Gdx.graphics.getWidth() / 15f);
		tabPanel.padBottom(Gdx.graphics.getHeight() / 15f);

		tabPanel.addTab("tab1", new Label("Tab1", Styles.LBL_BROWN));
		tabPanel.addTab("tab2", new Label("Tab2", Styles.LBL_BROWN));
		tabPanel.addTab("tab3", new Label("Tab3", Styles.LBL_BROWN));
		tabPanel.addTab("tab4", new Label("Tab4", Styles.LBL_BROWN));
	}

	@Override
	protected Stage createStage(int width, int height, Batch batch) {
		return new IngameControls(this, new ScreenViewport(), batch);
	}

	@Override
	protected void onDraw(Batch batch, float delta) {
		
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		iconManager.dispose();
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#onUpdate(float)
	 */
	@Override
	protected void onUpdate(float delta) {
		//iconManager.update();
	}

	@Override
	protected void onShow() {
	}

}
