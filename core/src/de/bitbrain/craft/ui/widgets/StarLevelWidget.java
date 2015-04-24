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

package de.bitbrain.craft.ui.widgets;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.animations.Animations.AnimationType;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.ui.UIFactory;

/**
 * Dispays levels horizontally via stars
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class StarLevelWidget extends Table {

  private static final int STAR_SIZE = 16;

  @Inject
  private TweenManager tweenManager;

  private int level, max;

  public StarLevelWidget(int level, int max) {
    SharedInjector.get().injectMembers(this);
    setMaximum(max);
    setLevel(level);
  }

  public void setLevel(int level) {
    if (level > 0 && level <= max) {
      this.level = level;
      updateStars();
    }
  }

  public void setMaximum(int max) {
    if (max > 0) {
      if (this.level > max) {
        setLevel(max);
      }
      this.max = max;
      updateStars();
    }
  }

  private void updateStars() {
    clearChildren();
    for (int i = 0; i < max; ++i) {
      Image star = generateStar(i, i < level);
      add(star).padRight(7f).padTop(22f);
    }
  }

  private Image generateStar(int index, boolean active) {
    Color color = Assets.CLR_YELLOW_SAND.cpy();
    if (!active) {
      color = new Color(0f, 0f, 0f, 0.35f);
    }
    Image star = UIFactory.createImage(Assets.TEX_STAR, STAR_SIZE, STAR_SIZE, color);
    if (active) {
      star.getColor().a = 0f;
      Tween.to(star, AnimationType.ALPHA.ordinal(), 0.4f).target(1f).ease(TweenEquations.easeInQuad).delay((index + 1) * 0.1f)
          .start(tweenManager);
    }
    return star;
  }
}
