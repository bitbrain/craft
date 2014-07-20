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

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.audio.ButtonSoundListener;
import de.bitbrain.craft.controls.TitleControls;
import de.bitbrain.craft.tweens.ActorTween;
import de.bitbrain.craft.tweens.SpriteTween;

/**
 * Title screen of the game
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class TitleScreen extends AbstractScreen {
	
	private Sprite logo;	
	
	private TextButton btnPlay;
	
	private Label lblCredits;

	public TitleScreen(CraftGame game) {
		super(game);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onCreateStage(com.badlogic.gdx.scenes.scene2d.Stage)
	 */
	@Override
	protected void onCreateStage(Stage stage) {
		btnPlay = new TextButton(Bundles.general.get(Bundles.START), Styles.TEXT_BUTTON);	
		
		final TitleScreen tempScreen = this;
		
		btnPlay.addCaptureListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				tempScreen.setScreen(new ProfessionScreen(tempScreen.game));
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchDown(event, x, y, pointer, button);
				btnPlay.padTop(5f);
				btnPlay.invalidate();
				return true;
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				btnPlay.padTop(0f);
				btnPlay.invalidate();
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#exit(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				btnPlay.padTop(0f);
				btnPlay.invalidate();
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#enter(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				
				if (btnPlay.isPressed()) {
					btnPlay.padTop(5f);
					btnPlay.invalidate();
				}
			}
			
		});
		
		btnPlay.addCaptureListener(new ButtonSoundListener());
		
		stage.addActor(btnPlay);
		
		LabelStyle lblStyle = new LabelStyle();
		lblStyle.fontColor = Assets.COLOR_BROWN_TEAK;
		lblStyle.font =  SharedAssetManager.get(Assets.FNT_SMALL, BitmapFont.class);
		lblCredits = new Label(Bundles.general.get(Bundles.CREDITS), lblStyle);
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
	 * @see de.bitbrain.craft.screens.MenuScreen#createStage()
	 */
	@Override
	protected Stage createStage(int width, int height, Batch batch) {
		return new TitleControls(new ScreenViewport(), batch);
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#onShow()
	 */
	@Override
	protected void onShow() {
		logo = new Sprite(SharedAssetManager.get(Assets.TEX_LOGO, Texture.class));
		logo.flip(false, true);
		
		//Music music = SharedAssetManager.get(Assets.MSC_MENU_01, Music.class);
		//music.setLooping(true);
		//music.setVolume(0.1f);
		//music.play();
		
	}
	
	@Override
	protected void onFadeIn(float parentInterval) {
		super.onFadeIn(parentInterval);
		logo.setColor(1f, 1f, 1f, 0f);
		
		Tween.to(logo, SpriteTween.ALPHA, parentInterval)
		  .target(1f)
		  .ease(TweenEquations.easeInOutCubic)
		  .start(tweenManager);
	}
	
	@Override
	protected void onFadeOut(float parentInterval) {
		super.onFadeOut(parentInterval);
		
		Tween.to(logo, SpriteTween.ALPHA, parentInterval)
		  .target(0f)
		  .ease(TweenEquations.easeInOutCubic)
		  .start(tweenManager);
	}

	@Override
	protected void afterFadeIn(float parentInterval) {
		super.afterFadeIn(parentInterval);
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
	
}	
