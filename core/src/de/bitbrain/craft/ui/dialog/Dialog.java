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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.graphics.GraphicsFactory;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.ui.Overlay;
import de.bitbrain.craft.ui.UIFactory;
import de.bitbrain.craft.util.Pair;

/**
 * Dialog which can be accepted or aborted
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Dialog extends Table {

  private static final float PADDING = 30f;

  @Inject
  private Overlay overlay;

  private Table buttonLayout;

  private NinePatch background;

  private Table window;

  private Cell<?> contentCell;

  private boolean visible;

  Dialog(Pair<String, ClickListener> submit, Pair<String, ClickListener> abort, Actor content) {
    SharedInjector.get().injectMembers(this);
    window = new Table();
    add(window);
    if (content != null) {
      contentCell = window.add(content).fill().align(Align.center).pad(PADDING);
    }
    if (submit != null || abort != null) {
      buttonLayout = new Table();
      window.row().row().padTop(PADDING);
      window.add(buttonLayout);
    }
    if (abort != null) {
      initAbort(abort);
    }
    if (submit != null) {
      Cell<?> c = initSubmit(submit);
      if (abort != null) {
        c.padLeft(PADDING);
      }
    }
    background = GraphicsFactory.createNinePatch(Assets.TEX_PANEL_BLACK_9patch, Sizes.panelTransparentRadius());
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    if (contentCell != null) {
      background.getColor().a = parentAlpha;
      background.draw(batch, window.getX() + contentCell.getActorX() - contentCell.getPadLeft(), window.getY()
          + contentCell.getActorY() - contentCell.getPadTop(), contentCell.getActorWidth() + contentCell.getPadRight()
          * 2, contentCell.getActorHeight() + contentCell.getPadBottom() * 2);
    }
    super.draw(batch, parentAlpha);
  }

  @Override
  public void invalidate() {
    setWidth(Sizes.worldWidth());
    setHeight(Sizes.worldHeight());
    window.setWidth(Sizes.worldWidth() / 2f);
    super.invalidate();
  }

  public boolean isVisible() {
    return visible;
  }

  public void show() {
    if (!isVisible()) {
      overlay.show(this);
      visible = true;
    }
  }

  public void hide() {
    if (isVisible()) {
      overlay.hide();
      visible = false;
    }
  }

  private Cell<TextButton> initSubmit(Pair<String, ClickListener> submit) {
    TextButton button = UIFactory.createPrimaryButton(submit.getFirst());
    if (submit.getSecond() != null) {
      button.addCaptureListener(submit.getSecond());
    }
    return buttonLayout.add(button);
  }

  private Cell<TextButton> initAbort(Pair<String, ClickListener> abort) {
    TextButton button = UIFactory.createAbortButton(abort.getFirst());
    if (abort.getSecond() != null) {
      button.addCaptureListener(abort.getSecond());
    }
    button.addCaptureListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        Dialog.this.hide();
      }
    });
    return buttonLayout.add(button);
  }
}
