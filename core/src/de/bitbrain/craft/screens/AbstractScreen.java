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

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.graphics.ParticleRenderer;
import de.bitbrain.craft.graphics.ScreenFader;
import de.bitbrain.craft.graphics.ScreenFader.FadeCallback;
import de.bitbrain.craft.graphics.UIRenderer;
import de.bitbrain.craft.ui.cli.CommandLineInterface;

/**
 * Abstract menu screen
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public abstract class AbstractScreen implements Screen, FadeCallback {
	
	@Inject
	protected OrthographicCamera camera;
	
	@Inject
	protected TweenManager tweenManager;
	
	@Inject
	protected ParticleRenderer particleRenderer;
	
	@Inject
	protected EventBus eventBus;
	
	@Inject
	private CraftGame game;
	
	@Inject
	private CommandLineInterface cli;
	
	private ScreenFader fader;
	
	private Class<? extends Screen> nextScreen;
	
	private Sprite background;
	
	protected Batch batch;
	
	private UIRenderer uiRenderer;
	
	public static final float FADE_INTERVAL = 0.7f;	
	
	public void setBackground(Sprite background) {
		this.background = background;
	}

	@Override
	public final void render(float delta) {
		
		Gdx.gl.glClearColor(0.08f, 0.02f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		onUpdate(delta);

		tweenManager.update(delta);
		
		camera.update();			
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			background.setBounds(
					camera.position.x - camera.viewportWidth / 2, 
					camera.position.y - camera.viewportHeight / 2, 
					camera.viewportWidth, 
					camera.viewportHeight);
			background.draw(batch);
			
			onDraw(batch, delta);
		batch.end();
		
		if (uiRenderer != null) {
			uiRenderer.render(delta);
			camera.update();
			batch.begin();
			batch.setProjectionMatrix(camera.combined);
			onStageDraw(batch, delta);
			batch.end();
		}
		
		batch.begin();
			particleRenderer.render(batch, delta);
		batch.end();
		
		uiRenderer.render(delta);
		fader.render(batch);
	}

	@Override
	public void resize(int width, int height) {
		
		if (uiRenderer == null) {
			uiRenderer = new UIRenderer(width, height, createViewport(), batch);
			fader = new ScreenFader(tweenManager);
			fader.setCallback(this);
			Gdx.input.setCatchBackKey(true);
			onCreateStage(uiRenderer.getBase());
			uiRenderer.getBase().addActor(cli);
			fader.fadeIn();
		} else {
			uiRenderer.resize(getWorldWidth(width), getWorldHeight(height));
		}
		camera.setToOrtho(true, getWorldWidth(width), getWorldHeight(height));
	}

	@Override
	public final void show() {
		System.out.println(tweenManager);
		eventBus.subscribe(this);
		batch = new SpriteBatch();
		background = new Sprite(SharedAssetManager.get(Assets.TEX_BACKGROUND_01, Texture.class));
		background.flip(false, true);
		onShow();
	}

	@Override
	public void dispose() {
		batch.dispose();
		uiRenderer.dispose();
	}

	@Override
	public void hide() { 
		eventBus.unsubscribe(this);
	}

	@Override
	public void pause() { }

	@Override
	public void resume() { }
	
	public void onStageDraw(Batch batch, float delta) { }
	
	public void setScreen(Class<? extends Screen> screen) {
		Gdx.input.setInputProcessor(null);
		Gdx.input.setCatchBackKey(true);
		nextScreen = screen;
		eventBus.unsubscribe(this);
		fader.fadeOut();
	}
	
	protected abstract void onCreateStage(Stage stage);
	
	protected abstract void onDraw(Batch batch, float delta);
	
	protected abstract void onShow();
	
	protected int getWorldWidth(int screenWidth) {
		return screenWidth;
	}
	
	protected int getWorldHeight(int screenHeight) {
		return screenHeight;
	}
	
	protected Viewport createViewport() {
		return new ScreenViewport();
	}
	
	protected void onUpdate(float delta) { }
	
	@Override
	public void afterFadeIn() {
		Gdx.input.setInputProcessor(uiRenderer.getBase());
		Gdx.input.setCatchBackKey(true);
	}
	
	@Override
	public void afterFadeOut() {		
		particleRenderer.clear();
		if (nextScreen != null) {
			game.setScreen(nextScreen);
		}
	}
	
	@Override
	public void beforeFadeIn() { }
	
	@Override
	public void beforeFadeOut() { }
}