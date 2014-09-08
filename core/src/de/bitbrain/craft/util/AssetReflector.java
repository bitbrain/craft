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

package de.bitbrain.craft.util;

import java.lang.reflect.Field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import de.bitbrain.craft.Assets;

/**
 * Reflects the asset class and loads all assets
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class AssetReflector {

	private AssetManager assetManager;
	
	public AssetReflector(AssetManager assetManager) {
		this.assetManager = assetManager;
	}
	
	public void load() {
		
		Gdx.app.log("LOAD", "Loading assets...");
		
		for (Field field : Assets.class.getDeclaredFields()) {	

			try {				
				if (field.getName().startsWith("TEX")) {					
					assetManager.load((String)field.get(null), Texture.class);
					Gdx.app.log("LOAD", "Texture '" + field.get(null) + "' loaded.");
				} else if (field.getName().startsWith("FNT")) {					
					assetManager.load((String)field.get(null), BitmapFont.class);
					Gdx.app.log("LOAD", "Font '" + field.get(null) + "' loaded.");
				} else if (field.getName().startsWith("SND")) {					
					assetManager.load((String)field.get(null), Sound.class);
					Gdx.app.log("LOAD", "Sound '" + field.get(null) + "' loaded.");
				} else if (field.getName().startsWith("MSC")) {					
					assetManager.load((String)field.get(null), Music.class);
					Gdx.app.log("LOAD", "Music '" + field.get(null) + "' loaded.");
				} else if (field.getName().startsWith("PRT")) {					
					assetManager.load((String)field.get(null), ParticleEffect.class);
					Gdx.app.log("LOAD", "Particle Effect '" + field.get(null) + "' loaded.");
				}
			} catch (IllegalArgumentException e) {
				Gdx.app.error("ERROR", "Could not load asset.", e);
			} catch (IllegalAccessException e) {
				Gdx.app.error("ERROR", "Could not load asset.", e);
			}
		}
		
		assetManager.finishLoading();
		Gdx.app.log("INFO", "Done loading assets.");
	}
}
