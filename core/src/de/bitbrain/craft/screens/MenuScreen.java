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

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.tweens.SpriteTween;

/**
 * Abstract menu screen
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public abstract class MenuScreen implements Screen {
	
	protected CraftGame game;
	
	private Sprite background;
	
	protected Batch batch;
	
	protected OrthographicCamera camera;		
	
	protected Stage stage;
	
	protected TweenManager tweenManager;
	
	
	public MenuScreen(CraftGame game) {
		this.game = game;		
	}

	@Override
	public final void render(float delta) {
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tweenManager.update(delta);
		
		if (stage != null)
			stage.act(delta);
		
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
		
		if (stage != null)
			stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
		if (stage == null) {
			stage = createStage(width, height, batch);
			Gdx.input.setInputProcessor(stage);
			onCreateStage(stage);
		} else {		
			stage.getViewport().update(width, height, true);
		}
		
		camera.setToOrtho(true, width, height);
	}

	@Override
	public final void show() {
		camera = new OrthographicCamera();	
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		background = new Sprite(SharedAssetManager.get(Assets.TEXTURE_BACKGROUND, Texture.class));
		background.flip(false, true);
		
		background.setColor(1f, 1f, 1f, 0f);
		
		Tween.to(background, SpriteTween.ALPHA, 0.3f)
			.ease(TweenEquations.easeInOutCubic)
			.target(1f)
			.start(tweenManager);
		
		onShow();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void hide() { }

	@Override
	public void pause() { }

	@Override
	public void resume() { }
	
	protected abstract void onCreateStage(Stage stage);
	
	protected abstract Stage createStage(int width, int height, Batch batch);
	
	protected abstract void onDraw(Batch batch, float delta);
	
	protected abstract void onShow();

}
