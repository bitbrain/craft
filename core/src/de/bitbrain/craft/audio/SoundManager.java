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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.SoundPlayEvent;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.models.SoundConfig;

/**
 * Manages sounds on runtime
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class SoundManager implements Disposable {

	public static final String DEFERRED_SOUNDS = Assets.DIR_AUDIO
			+ "/deferred/";

	private AssetManager assetManager;

	private ExecutorService executor;

	@Inject
	private EventBus eventBus;

	public SoundManager() {
		assetManager = new AssetManager();	
		executor = Executors.newFixedThreadPool(5);
	}
	
	@PostConstruct
	public void init() {
		eventBus.subscribe(this);
	}

	public void play(String id, final float volume, final float pitch, final float pan) {
		final String file = DEFERRED_SOUNDS + id;
		if (!assetManager.isLoaded(file, Sound.class)) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					assetManager.load(file, Sound.class);
					assetManager.finishLoading();
					Sound sound = assetManager.get(file, Sound.class);
					eventBus.fireEvent(new SoundPlayEvent(sound, volume, pitch, pan));
				}
			});
		} else {
			assetManager.get(file, Sound.class).play(volume, pitch, pan);
		}
	}

	public void play(SoundConfig config) {
		play(config.getFile(), config.getVolume(), config.getPitch(), config.getPan());
	}

	@Handler	
	public void onDeferredSoundPlay(SoundPlayEvent event) {
		Sound sound = event.getModel();
		Float volume = (Float) event.getParam(0);
		Float pitch = (Float) event.getParam(1);
		Float pan = (Float) event.getParam(2);
		sound.play(volume, pitch, pan);
	}

	@Override
	public void dispose() {
		executor.shutdown();
		assetManager.dispose();
		eventBus.unsubscribe(this);
	}

}
