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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.tweens.ActorTween;

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

	private Sprite workbench;

	public CraftingWidget(ProfessionLogic professionLogic) {
		SharedInjector.get().injectMembers(this);
		eventBus.subscribe(this);
		this.professionLogic = professionLogic;
		workbench = generateBackground(Profession.current);
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
		workbench.setColor(getColor());
		workbench.setBounds(getX(), getY(), getWidth(), getHeight());
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

	public void animate() {
		getColor().a = 0f;
		float targetY = getY();
		setY(Gdx.graphics.getHeight());
		Tween.to(this, ActorTween.ALPHA, 1.6f).target(0.9f)
				.ease(TweenEquations.easeInBack).start(tweenManager);
		Tween.to(this, ActorTween.Y, 2.0f).target(targetY)
				.ease(TweenEquations.easeInOutBounce).start(tweenManager);
	}

	@SuppressWarnings("incomplete-switch")
	private Sprite generateBackground(Profession profession) {
		Texture texture = SharedAssetManager
				.get(Assets.TEX_LOGO, Texture.class);
		switch (profession) {
		case ALCHEMIST:
			texture = SharedAssetManager.get(Assets.TEX_BOWL, Texture.class);
			break;
		}
		return new Sprite(texture);
	}

	private boolean collides(float x, float y) {
		y += getY();
		return x >= getX() && x <= getX() + getWidth() && y >= getY()
				&& y <= getY() + getHeight();
	}
}
