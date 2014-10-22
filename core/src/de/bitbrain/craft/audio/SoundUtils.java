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

package de.bitbrain.craft.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import de.bitbrain.craft.SharedAssetManager;

/**
 * Utility class for sound playback
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class SoundUtils {

	public static void play(String id, float volume, float pitch) {
		Sound sound = SharedAssetManager.get(id, Sound.class);
		
		if (sound != null) {
			sound.play(volume, pitch, 1.0f);
		} else {
			Gdx.app.error("ERROR", "[SoundUtils] Sound with id='" + id + "' not found.");
		}
	}
	
	public static void play(String id) {
		play(id, 1.0f, 1.0f);
	}
}
