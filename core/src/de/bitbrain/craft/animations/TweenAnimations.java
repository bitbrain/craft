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

package de.bitbrain.craft.animations;

import java.util.HashSet;
import java.util.Set;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenManager;

import com.google.inject.Inject;

import de.bitbrain.craft.inject.StateScoped;

/**
 * Contains complex animations for different views, widgets and components
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class TweenAnimations {

  public static final float DURATION_DEFAULT = 1f;

  @Inject
  private TweenManager tweenManager;

  public static enum TweenType {
    SCALE_X,
    SCALE_Y,
    POS_X,
    POS_Y,
    SCALE,
    FADE,
    WIDTH,
    HEIGHT,
    RED,
    GREEN,
    BLUE,
    ALPHA,
    BOUNCE,
    SIZE,
    VALUE,
    ROTATION,
    NONE;

    public static TweenType byIndex(int index) {
      TweenType[] types = values();
      if (index >= 0 && index < types.length) {
        return types[index];
      } else {
        return NONE;
      }
    }
  }

  public static interface TweenAnimationCallback {

    void onCompletion(TweenAnimation sender);
  }

  public class TweenAnimation {

    private Tween tween;

    private Set<TweenAnimation> siblings, afters;

    private Set<TweenAnimationCallback> callbacks;

    private Object target;

    private TweenType type;

    private TweenAnimation(Object target, TweenType type, float duration) {
      this.target = target;
      siblings = new HashSet<TweenAnimation>();
      afters = new HashSet<TweenAnimation>();
      callbacks = new HashSet<TweenAnimationCallback>();
      tween = Tween.to(target, type.ordinal(), duration);
      tween.setCallbackTriggers(TweenCallback.COMPLETE).setCallback(new TweenCallback() {
        @Override
        public void onEvent(int type, BaseTween<?> source) {
          triggerAfter();
          for (TweenAnimationCallback callback : callbacks) {
            callback.onCompletion(TweenAnimation.this);
          }
        }
      });
    }

    public TweenAnimation ease(TweenEquation equation) {
      tween.ease(equation);
      return this;
    }

    public TweenAnimation target(float targetValue) {
      tween.target(targetValue);
      return this;
    }

    public TweenAnimation repeat(int count, float delay) {
      tween.repeat(count, delay);
      return this;
    }

    public TweenAnimation repeatYoyo(int count, float delay) {
      tween.repeatYoyo(count, delay);
      return this;
    }

    public TweenAnimation delay(float delay) {
      tween.delay(delay);
      return this;
    }

    public TweenAnimation after(TweenAnimation animation) {
      afters.add(animation);
      return this;
    }

    public TweenAnimation append(TweenAnimation animation) {
      siblings.add(animation);
      return this;
    }

    public TweenAnimation callback(TweenAnimationCallback callback) {
      if (callback != null) {
        callbacks.add(callback);
      }
      return this;
    }

    public TweenType getType() {
      return type;
    }

    public void start() {
      tweenManager.killTarget(target);
      tween.start(tweenManager);
      for (TweenAnimation animation : siblings) {
        animation.start();
      }
    }

    private void triggerAfter() {
      for (TweenAnimation after : afters) {
        after.start();
      }
    }
  }

  public TweenAnimation create(Object target, TweenType type, float duration) {
    TweenAnimation animation = new TweenAnimation(target, type, duration);
    return animation;
  }

  public void killTarget(Object target) {
    tweenManager.killTarget(target);
  }

  public void killTarget(Object target, TweenType type) {
    tweenManager.killTarget(target, type.ordinal());
  }

  public void killAll() {
    tweenManager.killAll();
  }

  public TweenAnimation animate(Object target, TweenType type) {
    return create(target, type, DURATION_DEFAULT);
  }

}
