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
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.professions.ProfessionLogic;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.tweens.SpriteTween;

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

	private AnimatedBounceObject workbench, table;

	public CraftingWidget(ProfessionLogic professionLogic) {
		SharedInjector.get().injectMembers(this);
		eventBus.subscribe(this);
		this.professionLogic = professionLogic;
		table = new AnimatedBounceObject(Assets.TEX_TABLE);
		table.setSizeOffset(50f, 120f);
		table.setPositionOffset(0f, -190f);
		workbench = new AnimatedBounceObject(Assets.TEX_BOWL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.
	 * g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		table.draw(batch, parentAlpha);
		workbench.draw(batch, parentAlpha);
	}

	@Handler
	public void onEvent(MouseEvent<?> event) {
		if (event.getType().equals(EventType.MOUSEDROP)
				&& event.getModel() instanceof Item) {
			Item item = (Item) event.getModel();
			if (professionLogic.add(item)
					&& collides(event.getMouseX(), event.getMouseY())) {
				// Item accepted, remove it from system
				int amount = 1;
				if (event.getParam(0) != null) {
					amount = (Integer) event.getParam(0);
				}
				api.removeItem(Player.getCurrent().getId(), item.getId(),
						amount);
			}
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
		y += getY();
		return x >= getX() && x <= getX() + getWidth() && y >= getY()
				&& y <= getY() + getHeight();
	}

	private class AnimatedBounceObject {

		private Sprite background;

		private Vector2 sizeOffset, posOffset;

		public AnimatedBounceObject(String assetId) {
			sizeOffset = new Vector2();
			posOffset = new Vector2();
			background = new Sprite(SharedAssetManager.get(assetId,
					Texture.class));
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

		public void refresh() {
			background.setSize(getWidth() + sizeOffset.x, getHeight()
					+ sizeOffset.y);
			background.setPosition(getX() + posOffset.x - (getWidth() - (getWidth() - sizeOffset.x)) / 2f, 
					               getY() + posOffset.y);
		}

		public void animate(TweenManager tweenManager) {
			animate(tweenManager, 0f);
		}

		public void hide() {
			background.setY(Gdx.graphics.getHeight() + getHeight());
		}

		public void animate(TweenManager tweenManager, float delay) {
			refresh();
			tweenManager.killTarget(background);
			// Alpha fading
			background.setAlpha(0f);
			Tween.to(background, SpriteTween.ALPHA, 0.4f).target(1f)
					.ease(TweenEquations.easeInBack).start(tweenManager);
			// vertical bounce
			float originalY = background.getY();
			background.setY(Gdx.graphics.getHeight() + getHeight());
			Tween tween = Tween.to(background, SpriteTween.Y, 1.0f)
					.target(originalY).ease(TweenEquations.easeOutBounce)
					.delay(delay).start(tweenManager);
		}

		public void draw(Batch batch, float alphaModulation) {
			background.draw(batch, alphaModulation);
		}
	}
}
