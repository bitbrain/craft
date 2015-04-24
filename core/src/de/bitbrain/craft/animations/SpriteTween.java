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

import com.badlogic.gdx.graphics.g2d.Sprite;

import de.bitbrain.craft.animations.Animations.AnimationType;

/**
 * Tween accessor for sprites
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class SpriteTween extends AbstractTween<Sprite> {

  @Override
  public int getValues(Sprite target, AnimationType type, float[] returnValues) {
    switch (type) {
      case POS_X:
        returnValues[0] = target.getX();
        return 1;
      case POS_Y:
        returnValues[0] = target.getY();
        return 1;
      case ALPHA:
        returnValues[0] = target.getColor().a;
        return 1;
      case ROTATION:
        returnValues[0] = target.getRotation();
        return 1;
      case SCALE:
        returnValues[0] = target.getScaleX();
        return 1;
      default:
        return 0;
    }
  }

  @SuppressWarnings("incomplete-switch")
  @Override
  public void setValues(Sprite target, AnimationType type, float[] newValues) {
    switch (type) {
      case BOUNCE:
        target.setY(newValues[0]);
        break;
      case ALPHA:
        target.setAlpha(newValues[0]);
        break;
      case ROTATION:
        target.setRotation(newValues[0]);
        break;
      case SCALE:
        target.setScale(newValues[0]);
        break;
      case POS_X:
        target.setX(newValues[0]);
        break;
      case POS_Y:
        target.setY(newValues[0]);
        break;
    }
  }

}
