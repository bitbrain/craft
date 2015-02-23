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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.events.KeyEvent;
import de.bitbrain.craft.ui.UIFactory;
import de.bitbrain.craft.ui.dialog.Dialog;
import de.bitbrain.craft.ui.dialog.DialogBuilder;

/**
 * Title screen of the gameO
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class TitleScreen extends AbstractScreen {	
	
	private TextButton btnPlay;
	
	private Label lblCredits;

	private Dialog closeDialog;
	
	private Table layout;

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onCreateStage(com.badlogic.gdx.scenes.scene2d.Stage)
	 */
	@Override
	protected void onCreateStage(Stage stage) {
		
		layout = new Table();
		layout.setFillParent(true);
		
		// Logo
		Image logo = new Image(SharedAssetManager.get(Assets.TEX_LOGO, Texture.class));
		layout.add(logo)
			  .padTop(70f)
			  .center()
			  .width(logo.getWidth() * 2f)
			  .height(logo.getHeight() * 2f);
		layout.row().padTop(80f);
		
		// Play button
		btnPlay = UIFactory.createPrimaryButton(Bundles.general.get(Bundles.START));
		btnPlay.addCaptureListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				TitleScreen.this.setScreen(ProfessionScreen.class);
			}
		});		
		layout.add(btnPlay)
		      .width(btnPlay.getWidth() * 1.15f)
		      .height(btnPlay.getHeight() * 1.15f)
		      .row().padTop(70f);
		btnPlay.getLabel().setFontScale(1.3f);
		
		// Credits
		lblCredits = new Label(Bundles.general.get(Bundles.CREDITS), Styles.LBL_BROWN);
		layout.add(lblCredits).row();
		
		stage.addActor(layout);
		
		// Dialog
		DialogBuilder dBuilder = new DialogBuilder();
		closeDialog = dBuilder.content("Do you really want to quit the game?")
				.enableAbort("No")
				.enableSubmit("Yes", new ClickListener() {
					 @Override
					public void clicked(InputEvent event, float x, float y) {
						Gdx.app.exit();
					}
				}).build(false);
		
	}
	
	@Handler
	void onEvent(KeyEvent event) {
		if (event.getKey() == Keys.ESCAPE || event.getKey() == Keys.BACK) {
			closeDialog.show();
		}
	}

	@Override
	protected void onDraw(Batch batch, float delta) { }

	@Override
	protected void onShow() {	}

	@Override
	protected Viewport createViewport() {
		return new FillViewport(Sizes.worldWidth(), Sizes.worldHeight());
	}
}
