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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.inject.Inject;

import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.db.DriverProvider;
import de.bitbrain.craft.graphics.IconManager;
import de.bitbrain.craft.graphics.ParticleRenderer;
import de.bitbrain.craft.graphics.shader.BlurShader;
import de.bitbrain.craft.migration.DataMigrator;
import de.bitbrain.craft.tweens.ActorTween;
import de.bitbrain.craft.tweens.BlurShaderTween;
import de.bitbrain.craft.tweens.FadeableTween;
import de.bitbrain.craft.tweens.FloatValueTween;
import de.bitbrain.craft.tweens.SpriteTween;
import de.bitbrain.craft.tweens.VectorTween;
import de.bitbrain.craft.ui.LoadingIndicator;
import de.bitbrain.craft.util.AssetReflector;
import de.bitbrain.craft.util.FloatValueProvider;

/**
 * Screen which is shown in the very beginning
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class LoadingScreen extends AbstractScreen {

	@Inject
	private DataMigrator migrator;

	private ExecutorService executor;

	private Future<?> future;
	
	private AssetReflector reflector;

	public LoadingScreen() {
		executor = Executors.newFixedThreadPool(5);
	}

	private void registerTweens() {
		Gdx.app.log("INFO", "Registering tweens...");
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		Tween.registerAccessor(Actor.class, new ActorTween());
		Tween.registerAccessor(IconManager.class, new FadeableTween());
		Tween.registerAccessor(LoadingIndicator.class, new FadeableTween());
		Tween.registerAccessor(ParticleRenderer.class, new FadeableTween());
		Tween.registerAccessor(Vector2.class, new VectorTween());
		Tween.registerAccessor(BlurShader.class, new BlurShaderTween());
		Tween.registerAccessor(FloatValueProvider.class, new FloatValueTween());
		Gdx.app.log("INFO", "Tween accessors registered.");
	}

	@Override
	protected void onCreateStage(Stage stage) {
		Table layout = new Table();
		layout.setFillParent(true);
		LoadingIndicator indicator = new LoadingIndicator(tweenManager);
		indicator.setWidth(150f);
		indicator.setHeight(150f);
		layout.add(indicator);
		stage.addActor(layout);
	}

	@Override
	protected void onDraw(Batch batch, float delta) {
		if (future.isDone() && !reflector.loadNext()) {
			Styles.load();
			Gdx.app.log("INFO", "Done loading assets.");
			game.setScreen(TitleScreen.class);
		}
	}

	@Override
	protected void onShow() {
		registerTweens();
		future = executor.submit(new GameLoader());
		reflector = new AssetReflector(SharedAssetManager.getInstance());
	}

	private class GameLoader implements Runnable {
		@Override
		public void run() {
			try {
				DriverProvider.initialize();
				Bundles.load();
				migrator.migrate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected Viewport createViewport() {
		return new FillViewport(Sizes.worldWidth(), Sizes.worldHeight());
	}
}