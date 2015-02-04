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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.graphics.GraphicsFactory;
import de.bitbrain.craft.graphics.Icon;
import de.bitbrain.craft.graphics.IconManager;
import de.bitbrain.craft.graphics.IconManager.IconDrawable;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.tweens.ValueTween;
import de.bitbrain.craft.util.ValueProvider;

/**
 * An icon which also shows rarity and special effects
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class IconWidget extends Actor implements ValueProvider {

	public float iconScale;

	private NinePatch background;

	private Label amountLabel;

	private IconDrawable icon;

	private int currentAmount, amount;

	@Inject
	private TweenManager tweenManager;

	@Inject
	private IconManager iconManager;

	public IconWidget(Icon icon, int amount) {
		Tween.registerAccessor(IconWidget.class, new ValueTween());
		SharedInjector.get().injectMembers(this);
		this.amount = amount;
		amountLabel = new Label("1", Styles.LBL_TOOLTIP);
		amountLabel.setFontScale(2.1f);
		background = GraphicsFactory.createNinePatch(
				Assets.TEX_PANEL_TRANSPARENT_9patch,
				Sizes.panelTransparentRadius());
		this.icon = iconManager.fetch(icon);
		this.currentAmount = amount;
	}

	public final void setSource(Icon icon, int amount) {
		this.icon = iconManager.fetch(icon);
		tweenManager.killTarget(this);
		Tween.to(this, ValueTween.VALUE, 1f).target(amount)
				.ease(TweenEquations.easeOutQuart).start(tweenManager);
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

		float iconScale = 0.8f;

		// background
		background.setColor(getColor());
		background.draw(batch, getX(), getY(), getWidth(), getHeight());

		// Icon
		icon.width = getWidth() * iconScale;
		icon.height = getHeight() * iconScale;
		icon.x = getX() + (getWidth() - icon.width) / 2;
		icon.y = getY() + (getHeight() - icon.height) / 2;
		icon.rotation = 180f;
		icon.color = getColor();
		icon.draw(batch, parentAlpha);

		// Amount
		if (amount > 0) {
			amountLabel.setText(String.valueOf(currentAmount));
			amountLabel.setX(getX() + getWidth() - amountLabel.getPrefWidth()
					- getPadding() / 2f);
			amountLabel.setY(getY() + getPadding());
			amountLabel.draw(batch, parentAlpha);
		}
	}

	private float getPadding() {
		return 16f;
	}

	@Override
	public int getValue() {
		return currentAmount;
	}

	@Override
	public void setValue(int value) {
		currentAmount = value;
	}
}