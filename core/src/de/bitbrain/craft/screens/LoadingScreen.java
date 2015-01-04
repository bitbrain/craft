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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.IconManager;
import de.bitbrain.craft.db.DriverProvider;
import de.bitbrain.craft.graphics.ParticleRenderer;
import de.bitbrain.craft.graphics.shader.BlurShader;
import de.bitbrain.craft.migration.DataMigrator;
import de.bitbrain.craft.tweens.ActorTween;
import de.bitbrain.craft.tweens.BlurShaderTween;
import de.bitbrain.craft.tweens.FadeableTween;
import de.bitbrain.craft.tweens.SpriteTween;
import de.bitbrain.craft.tweens.VectorTween;
import de.bitbrain.craft.util.AssetReflector;

/**
 * Screen which is shown in the very beginning
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class LoadingScreen implements Screen {
	
	@Inject
	private DataMigrator migrator;
	
	@Inject
	private TitleScreen screen;
	
	@Inject
	private CraftGame game;
	
	private boolean drawed = false;
	
	private Texture background;
	
	private Batch batch;

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		if (drawed) {
			loadGame();
		} else {
			batch.begin();
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.end();
			drawed = true;
		}
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		background = new Texture(Gdx.files.internal("images/loadscreen.png"));
		batch = new SpriteBatch();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		background.dispose();
	}
	
	private void loadGame() {
		DriverProvider.initialize();
		loadResources();	
		registerTweens();
		//loadCursor();
		Bundles.load();
		migrator.migrate();
		game.setScreen(screen);
	}
	
	private void loadResources() {
		AssetManager mgr = SharedAssetManager.getInstance();		
		AssetReflector reflector = new AssetReflector(mgr);		
		reflector.load();		
		Styles.load();
	}
	
	private void registerTweens() {
		Gdx.app.log("INFO", "Registering tweens...");
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		Tween.registerAccessor(Actor.class, new ActorTween());
		Tween.registerAccessor(IconManager.class, new FadeableTween());
		Tween.registerAccessor(ParticleRenderer.class, new FadeableTween());
		Tween.registerAccessor(Vector2.class, new VectorTween());
		Tween.registerAccessor(BlurShader.class, new BlurShaderTween());
		Gdx.app.log("INFO", "Tween accessors registered.");
	}
	
	@SuppressWarnings("unused")
	private void loadCursor() {
		Pixmap pm = new Pixmap(Gdx.files.internal("images/cursor.png"));
        int xHotSpot = pm.getWidth() / 2;
        int yHotSpot = pm.getHeight() / 2;        
        Gdx.input.setCursorImage(pm, xHotSpot, yHotSpot);
        pm.dispose();
	}
}
