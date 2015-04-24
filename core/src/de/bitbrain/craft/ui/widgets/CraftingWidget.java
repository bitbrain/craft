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

import net.engio.mbassy.listener.Handler;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.animations.Animations.AnimationType;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.professions.ProfessionLogic;
import de.bitbrain.craft.events.Event;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.GestureManager;
import de.bitbrain.craft.events.ItemEvent;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.graphics.GraphicsFactory;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;

/**
 * General view component for professions
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class CraftingWidget extends Actor {

  private ProfessionLogic professionLogic;

  @Inject
  private EventBus eventBus;

  @Inject
  private API api;

  @Inject
  private TweenManager tweenManager;

  @Inject
  private GestureManager gestureManager;

  private AnimatedBounceObject workbench, table;

  private Item dragItem;

  private int dragAmount;

  public CraftingWidget(ProfessionLogic professionLogic) {
    SharedInjector.get().injectMembers(this);
    eventBus.subscribe(this);
    this.professionLogic = professionLogic;
    table =
        new AnimatedBounceObject(Assets.TEX_TABLE, GraphicsFactory.createTexture(32, 16, new Color(0f, 0f, 0f, 0.2f)));
    table.setSizeOffset(50f, 120f);
    table.setPositionOffset(0f, -190f);
    table.setShadowSizeOffset(0f, -20f);
    table.setShadowPositionOffset(0f, -250f);
    workbench = new AnimatedBounceObject(Assets.TEX_BOWL, Assets.TEX_CIRCLE);
    workbench.setShadowSizeOffset(-40f, -50f);
    workbench.setShadowPositionOffset(0f, -30f);
    registerEvents();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics. g2d.Batch, float)
   */
  @Override
  public void draw(Batch batch, float parentAlpha) {
    table.draw(batch, parentAlpha);
    workbench.draw(batch, parentAlpha);
  }

  @Handler
  public void onEvent(MouseEvent<?> event) {
    if (event.getType().equals(EventType.MOUSEDROP) && event.getModel() instanceof Item) {
      Item item = (Item) event.getModel();
      // Item accepted, remove it from system
      int amount = 1;
      if (event.hasParam(ItemEvent.AMOUNT)) {
        amount = (Integer) event.getParam(ItemEvent.AMOUNT);
      }
      if (collides(event.getMouseX(), event.getMouseY())) {
        professionLogic.add(item, amount);
        api.removeItem(Player.getCurrent().getId(), item.getId(), amount);
        // Check if the element got dropped by this widget
      } else if (event.hasParam(Event.SENDER) && event.getParam(Event.SENDER).equals(this)) {
        api.addItem(Player.getCurrent().getId(), item.getId(), amount);
      }
    }
  }

  @Handler
  public void onEvent(ItemEvent event) {
    if (event.getType().equals(EventType.CRAFT_REMOVE)) {
      dragItem = event.getModel();
      dragAmount = event.getAmount();
      MouseEvent<Item> forwardedEvent =
          new MouseEvent<Item>(EventType.MOUSEDRAG, event.getModel(), Sizes.localMouseX(), Sizes.localMouseY());
      forwardedEvent.setParam(ItemEvent.ITEM, dragItem);
      forwardedEvent.setParam(ItemEvent.AMOUNT, dragAmount);
      eventBus.fireEvent(forwardedEvent);
    }
  }

  @Override
  public void setY(float y) {
    super.setY(y);
    table.refresh();
    workbench.refresh();
  }

  public void animate() {
    workbench.hide();
    table.animate(tweenManager);
    workbench.animate(tweenManager, 0.4f);
  }

  private boolean collides(float x, float y) {
    x /= Sizes.worldScreenFactorX();
    y /= Sizes.worldScreenFactorY();
    y += getHeight() / 1.2f;
    return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
  }

  private void registerEvents() {
    gestureManager.addListener(professionLogic.getCraftingGesture());
    addListener(new DragListener() {
      @Override
      public void dragStart(InputEvent event, float x, float y, int pointer) {
        professionLogic.fetch();
      }

      @Override
      public void dragStop(InputEvent event, float x, float y, int pointer) {
        MouseEvent<Item> mouseEvent =
            new MouseEvent<Item>(EventType.MOUSEDROP, dragItem, Sizes.localMouseX(), Sizes.localMouseY());
        mouseEvent.setParam(Event.SENDER, CraftingWidget.this);
        mouseEvent.setParam(ItemEvent.AMOUNT, dragAmount);
        if (!collides(Sizes.localMouseX(), Sizes.localMouseY())) {
          mouseEvent.setParam(Event.SOURCE_X, 0f);
          mouseEvent.setParam(Event.SOURCE_Y, 0f);
        }
        eventBus.fireEvent(mouseEvent);
        dragItem = null;
        dragAmount = 0;
      }
    });
  }

  private class AnimatedBounceObject {

    private Sprite background, shadow;

    private Vector2 sizeOffset, posOffset, shadowSizeOffset, shadowPosOffset;

    public AnimatedBounceObject(String assetId, Texture shadowAsset) {
      sizeOffset = new Vector2();
      posOffset = new Vector2();
      shadowSizeOffset = new Vector2();
      shadowPosOffset = new Vector2();
      background = new Sprite(SharedAssetManager.get(assetId, Texture.class));
      shadow = new Sprite(shadowAsset);
    }

    public AnimatedBounceObject(String assetId, String shadowAssetId) {
      this(assetId, SharedAssetManager.get(shadowAssetId, Texture.class));
    }

    public void setSizeOffset(float offsetX, float offsetY) {
      sizeOffset.x = offsetX;
      sizeOffset.y = offsetY;
      refresh();
    }

    public void setPositionOffset(float offsetX, float offsetY) {
      posOffset.x = offsetX;
      posOffset.y = offsetY;
      refresh();
    }

    public void setShadowSizeOffset(float offsetX, float offsetY) {
      shadowSizeOffset.x = offsetX;
      shadowSizeOffset.y = offsetY;
      refreshShadow();
    }

    public void setShadowPositionOffset(float offsetX, float offsetY) {
      shadowPosOffset.x = offsetX;
      shadowPosOffset.y = offsetY;
      refreshShadow();
    }

    public void refresh() {
      refreshShadow();
      background.setSize(getWidth() + sizeOffset.x, getHeight() + sizeOffset.y);
      background.setPosition(getX() + posOffset.x - (getWidth() - (getWidth() - sizeOffset.x)) / 2f, getY()
          + posOffset.y);
    }

    public void refreshShadow() {
      shadow.setSize(getWidth() + shadowSizeOffset.x, getHeight() + shadowSizeOffset.y);
      shadow.setPosition(getX() + shadowPosOffset.x - (getWidth() - (getWidth() - shadowSizeOffset.x)) / 2f, getY()
          + shadowPosOffset.y);
    }

    public void animate(TweenManager tweenManager) {
      animate(tweenManager, 0f);
    }

    public void hide() {
      background.setY(Gdx.graphics.getHeight() + getHeight());
    }

    public void animate(TweenManager tweenManager, float delay) {
      refresh();
      tweenManager.killTarget(shadow);
      tweenManager.killTarget(background);
      // Alpha fading
      shadow.setAlpha(0f);
      background.setAlpha(0f);
      Tween.to(background, AnimationType.ALPHA.ordinal(), 0.4f).target(1f).ease(TweenEquations.easeInCubic).start(tweenManager);
      Tween.to(shadow, AnimationType.ALPHA.ordinal(), 0.4f).target(1f).ease(TweenEquations.easeInCubic).start(tweenManager);
      // vertical bounce
      float originalY = background.getY();
      background.setY(Gdx.graphics.getHeight() + getHeight());
      Tween.to(background, AnimationType.POS_Y.ordinal(), 1.0f).target(originalY).ease(TweenEquations.easeOutBounce).delay(delay)
          .start(tweenManager);
      // Shadow scaling
      shadow.setOrigin(shadow.getWidth() / 2f, shadow.getHeight() / 2f);
      shadow.setScale(0.0f);
      Tween.to(shadow, AnimationType.SCALE.ordinal(), 1.0f).target(1f).ease(TweenEquations.easeOutBounce).delay(delay).delay(0.2f)
          .start(tweenManager);
    }

    public void draw(Batch batch, float alphaModulation) {
      shadow.draw(batch, alphaModulation);
      background.draw(batch, alphaModulation);
    }
  }
}
