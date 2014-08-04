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

package de.bitbrain.craft;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.bitbrain.craft.db.DatabaseHelper;
import de.bitbrain.craft.screens.TitleScreen;
import de.bitbrain.craft.tweens.ActorTween;
import de.bitbrain.craft.tweens.FadeableTween;
import de.bitbrain.craft.tweens.SpriteTween;
import de.bitbrain.craft.util.AssetReflector;
import de.bitbrain.craft.util.Fadeable;
import de.myreality.jpersis.MapperManager;
import de.myreality.jpersis.db.DatabaseException;

/**
 * Main game file which handles all screens
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class CraftGame extends Game {

	@Override
	public void create () {
		
		Gdx.app.setLogLevel(Settings.LOGLEVEL);
		
		showStartupMessage();
		
		loadResources();
		DatabaseHelper.connect();
		registerTweens();
		//loadCursor();
		Bundles.load();
		TitleScreen screen = new TitleScreen(this);
		setScreen(screen);	
	}
	
	@Override
	public void resume() {
		super.resume();
		
		loadResources();
		DatabaseHelper.connect();
	}
	
	@Override
	public void dispose() {
		SharedAssetManager.dispose();
		
		try {
			MapperManager.getInstance().getConnector().closeConnection();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	private void showStartupMessage() {
		Gdx.app.log("STARTUP", "Craft v. " + Settings.VERSION + " (" + Settings.PHASE + ")");

	}
	
	private void loadResources() {
		AssetManager mgr = SharedAssetManager.getInstance();		
		AssetReflector reflector = new AssetReflector(mgr);
		
		reflector.load();		
		Styles.load();
	}
	
	private void registerTweens() {
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		Tween.registerAccessor(Actor.class, new ActorTween());
		Tween.registerAccessor(Fadeable.class, new FadeableTween());
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
