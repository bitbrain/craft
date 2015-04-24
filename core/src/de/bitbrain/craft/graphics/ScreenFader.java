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
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.bitbrain.craft.animations.TweenAnimations.TweenType;

/**
 * Fades screen in and out
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ScreenFader implements TweenCallback {

  public static final float DEFAULT_INTERVAL = 0.7f;

  private FadeCallback callback;

  private float interval = DEFAULT_INTERVAL;

  private boolean fadeIn = true;

  private Sprite sprite;

  private TweenManager tweenManager;

  private int width, height;

  public ScreenFader(TweenManager tweenManager) {
    this.tweenManager = tweenManager;
  }

  public void resize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void setInterval(float seconds) {
    this.interval = seconds;
  }

  public void setCallback(FadeCallback callback) {
    this.callback = callback;
  }

  public void fadeIn() {
    init();
    fadeIn = true;
    tweenManager.killTarget(sprite);
    sprite.setAlpha(1f);
    Tween.to(sprite, TweenType.ALPHA.ordinal(), interval).target(0f).setCallbackTriggers(TweenCallback.COMPLETE)
        .setCallback(this).ease(TweenEquations.easeOutCubic).start(tweenManager);
    if (callback != null) {
      callback.beforeFadeIn();
    }
  }

  public void fadeOut() {
    init();
    fadeIn = false;
    tweenManager.killTarget(sprite);
    Tween.to(sprite, TweenType.ALPHA.ordinal(), interval).target(1f).setCallbackTriggers(TweenCallback.COMPLETE)
        .setCallback(this).ease(TweenEquations.easeOutCubic).start(tweenManager);
    if (callback != null) {
      callback.beforeFadeOut();
    }
  }

  public void render(Batch batch) {
    if (sprite != null) {
      sprite.setBounds(0, 0, width, height);
      batch.begin();
      sprite.draw(batch);
      batch.end();
    }
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

  private void init() {
    if (sprite == null) {
      sprite = new Sprite(GraphicsFactory.createTexture(16, 16, Color.BLACK));
    }
  }

  public static interface FadeCallback {
    void beforeFadeIn();

    void beforeFadeOut();

    void afterFadeIn();

    void afterFadeOut();
  }
}