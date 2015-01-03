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
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.events.KeyEvent;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.tweens.ActorTween;
import de.bitbrain.craft.ui.UIFactory;

/**
 * Title screen of the gameO
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class TitleScreen extends AbstractScreen {
	
	private Sprite logo;	
	
	private TextButton btnPlay;
	
	private Label lblCredits;

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onCreateStage(com.badlogic.gdx.scenes.scene2d.Stage)
	 */
	@Override
	protected void onCreateStage(Stage stage) {
		btnPlay = UIFactory.createPrimaryButton(Bundles.general.get(Bundles.START));

		final TitleScreen tempScreen = this;
		
		btnPlay.addCaptureListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				tempScreen.setScreen(ProfessionScreen.class);
			}
		});
		
		stage.addActor(btnPlay);
		lblCredits = new Label(Bundles.general.get(Bundles.CREDITS), Styles.LBL_BROWN);
		stage.addActor(lblCredits);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onDraw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	protected void onDraw(Batch batch, float delta) {		
		logo.draw(batch);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		float scale = Gdx.graphics.getWidth() / 550.0f;
		
		logo.setX(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2);
		logo.setY(Gdx.graphics.getHeight() / 4.1f);
		logo.setScale(scale);
		
		btnPlay.setWidth(logo.getWidth() * scale / 1.5f);
		btnPlay.setHeight(logo.getHeight() * scale);
		btnPlay.setX(Gdx.graphics.getWidth() / 2.0f - btnPlay.getWidth() / 2.0f);
		btnPlay.setY(Gdx.graphics.getHeight() / 4.1f);
		btnPlay.getLabel().setFontScale(scale / 1.5f);
		
		lblCredits.setFontScale(scale / 1.5f);
		lblCredits.setX(Gdx.graphics.getWidth() / 2 - (lblCredits.getWidth() * lblCredits.getFontScaleX()) / 2);
		lblCredits.setY(Gdx.graphics.getHeight() / 15f);
		
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onShow()
	 */
	@Override
	protected void onShow() {
		logo = new Sprite(SharedAssetManager.get(Assets.TEX_LOGO, Texture.class));
		logo.flip(false, true);
	}

	@Override
	public void afterFadeIn() {
		super.afterFadeIn();
		btnPlay.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		final float INTERVAL = 0.6f;		
		Tween.to(btnPlay, ActorTween.SCALE, INTERVAL)
		.repeatYoyo(Tween.INFINITY, 0f)
		.ease(TweenEquations.easeNone)
		.target(0.7f)
		.start(tweenManager);
		
		Tween.to(btnPlay, ActorTween.ALPHA, INTERVAL)
		.repeatYoyo(Tween.INFINITY, 0f)
		.ease(TweenEquations.easeNone)
		.target(0.8f)
		.start(tweenManager);
		Tween.to(btnPlay.getLabel(), ActorTween.ALPHA, INTERVAL)
		.repeatYoyo(Tween.INFINITY, 0f)
		.ease(TweenEquations.easeNone)
		.target(0.8f)
		.start(tweenManager);
	}
	
	@Handler
	void onEvent(KeyEvent event) {
		if (event.getKey() == Keys.ESCAPE || event.getKey() == Keys.BACK) {
			Gdx.app.exit();
		}
	}
}	
