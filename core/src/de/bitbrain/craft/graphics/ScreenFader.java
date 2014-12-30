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

package de.bitbrain.craft.graphics;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.google.inject.Inject;

import de.bitbrain.craft.inject.StateScoped;

/**
 * Fades screen in and out
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class ScreenFader implements TweenCallback {
	
	public static final float DEFAULT_INTERVAL = 800;
	
	@Inject
	private TweenManager tweenManager;
	
	private FadeCallback callback;
	
	private float interval = DEFAULT_INTERVAL;
	
	private boolean fadeIn = true;
	
	public void setInterval(float seconds) {
		this.interval = seconds;
	}
	
	public void setCallback(FadeCallback callback) {
		this.callback = callback;
	}

	public void fadeIn() {
		fadeIn = true;
		onEvent(0, null);
	}
	
	public void fadeOut() {
		fadeIn = false;
		onEvent(0, null);
	}
	
	public void render(Batch batch) {
		
	}

	@Override
	public void onEvent(int type, BaseTween<?> source) {
		if (callback != null) {
			if (fadeIn) {
				callback.afterFadeIn();
			} else {
				callback.afterFadeOut();
			}
		}
	}
	
	public static interface FadeCallback {
		void beforeFadeIn();		
		void beforeFadeOut();
		void afterFadeIn();		
		void afterFadeOut();
	}
}