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

package de.bitbrain.craft.ui.elements;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.IconManager.IconDrawable;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.tweens.ValueTween;
import de.bitbrain.craft.util.ColorCalculator;
import de.bitbrain.craft.util.ValueProvider;

/**
 * An icon which also shows rarity and special effects
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ElementIcon extends Actor implements ValueProvider {
	
	public float iconScale;
	
	private ElementData data;
	
	private NinePatch background;
	
	private Label amount;
	
	private ColorCalculator colorCalculator;
	
	private IconDrawable icon;
	
	private Color backgroundColor = new Color(Color.WHITE);
	
	private int currentAmount;
	
	@Inject
	private TweenManager tweenManager;
	
	public ElementIcon(ElementData data) {
		Tween.registerAccessor(ElementIcon.class, new ValueTween());
		SharedInjector.get().injectMembers(this);
		setSource(data);
		amount = new Label("1", Styles.LBL_TEXT);
		background = Styles.ninePatch(Assets.TEX_PANEL_TRANSPARENT_9patch, Sizes.panelTransparentRadius());
		colorCalculator = new ColorCalculator();
		icon = data.getIcon();
		updateBackground();
	}
	
	public final void setSource(ElementData data) {
		this.data = data;
		tweenManager.killTarget(this);
		Tween.to(this, ValueTween.VALUE, 1f)
			 .delay(0.3f)
	         .target(this.data.getAmount())
	         .ease(TweenEquations.easeOutQuart)
	         .start(tweenManager);
	}
	
	private void updateBackground() {
		backgroundColor = colorCalculator.getColor(icon.getTexture());		
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {		
			
		float iconScale = 0.8f;

		if (icon != data.getIcon() || !icon.color.equals(data.getIcon().color)) {
			updateBackground();
		}
		
		// background
		background.setColor(backgroundColor);
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
		if (data.getAmount() > -1) {
			amount.setText(String.valueOf(currentAmount));
			amount.setX(getX() + getWidth() - amount.getPrefWidth() - getPadding());
			amount.setY(getY() + getPadding());
			amount.draw(batch, parentAlpha);
		}
	}
	
	private float getPadding() {
		return 12f;
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