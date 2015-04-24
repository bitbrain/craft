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

package de.bitbrain.craft.ui.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitbrain.craft.Styles;
import de.bitbrain.craft.util.Pair;

/**
 * Builder to build and show {@link Dialog}
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class DialogBuilder {

  private Pair<String, ClickListener> abort;
  private Pair<String, ClickListener> submit;

  private Actor content;

  public DialogBuilder disableAbort() {
    abort = null;
    return this;
  }

  public DialogBuilder enableAbort(String text, ClickListener listener) {
    abort = new Pair<String, ClickListener>(text, listener);
    return this;
  }

  public DialogBuilder enableAbort(String text) {
    return enableAbort(text, null);
  }

  public DialogBuilder disableSubmit() {
    submit = null;
    return this;
  }

  public DialogBuilder enableSubmit(String text, ClickListener listener) {
    submit = new Pair<String, ClickListener>(text, listener);
    return this;
  }

  public DialogBuilder enableSubmit(String text) {
    return enableSubmit(text, null);
  }

  public DialogBuilder content(Actor actor) {
    content = actor;
    return this;
  }

  public DialogBuilder content(String text) {
    Label label = new Label(text, Styles.LBL_ITEM);
    label.setWrap(true);
    return content(label);
  }

  public Dialog build(boolean show) {
    Dialog dialog = new Dialog(submit, abort, content);
    if (show) {
      dialog.show();
    }
    return dialog;
  }

  public Dialog build() {
    return build(true);
  }
}
