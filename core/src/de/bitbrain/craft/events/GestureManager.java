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

package de.bitbrain.craft.events;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.google.inject.Singleton;

/**
 * Handles gesture events
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@Singleton
public class GestureManager implements InputProcessor {

  private List<GestureDetector> detectors;

  public GestureManager() {
    detectors = new ArrayList<GestureDetector>();
  }

  public void addListener(GestureListener listener) {
    detectors.add(new GestureDetector(listener));
  }

  public void clear() {
    detectors.clear();
  }

  @Override
  public boolean keyDown(int keycode) {
    boolean value = false;
    for (GestureDetector detector : detectors) {
      if (detector.keyDown(keycode)) {
        value = true;
      }
    }
    return value;
  }

  @Override
  public boolean keyUp(int keycode) {
    boolean value = false;
    for (GestureDetector detector : detectors) {
      if (detector.keyUp(keycode)) {
        value = true;
      }
    }
    return value;
  }

  @Override
  public boolean keyTyped(char character) {
    boolean value = false;
    for (GestureDetector detector : detectors) {
      if (detector.keyTyped(character)) {
        value = true;
      }
    }
    return value;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    boolean value = false;
    for (GestureDetector detector : detectors) {
      if (detector.touchDown(screenX, screenY, pointer, button)) {
        value = true;
      }
    }
    return value;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    boolean value = false;
    for (GestureDetector detector : detectors) {
      if (detector.touchUp(screenX, screenY, pointer, button)) {
        value = true;
      }
    }
    return value;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    boolean value = false;
    for (GestureDetector detector : detectors) {
      if (detector.touchDragged(screenX, screenY, pointer)) {
        value = true;
      }
    }
    return value;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    boolean value = false;
    for (GestureDetector detector : detectors) {
      if (detector.mouseMoved(screenX, screenY)) {
        value = true;
      }
    }
    return value;
  }

  @Override
  public boolean scrolled(int amount) {
    boolean value = false;
    for (GestureDetector detector : detectors) {
      if (detector.scrolled(amount)) {
        value = true;
      }
    }
    return value;
  }

}
