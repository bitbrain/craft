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

package de.bitbrain.craft.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.IconManager.IconDrawable;
import de.bitbrain.craft.util.ColorCalculator;

/**
 * An icon which also shows rarity and special effects
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ElementIcon extends Actor {
	
	private static final float PADDING = 8f;
	
	public float iconScale;
	
	private ElementData data;
	
	private Sprite background;
	
	private Label amount;
	
	private ColorCalculator colorCalculator;
	
	private IconDrawable icon;
	
	private Color backgroundColor = new Color(Color.WHITE);
	
	public ElementIcon(ElementData data) {
		setSource(data);
		amount = new Label("1", Styles.LBL_TEXT);
		background = new Sprite(SharedAssetManager.get(Assets.TEX_ICON_BACKGROUND, Texture.class));
		colorCalculator = new ColorCalculator();
		icon = data.getIcon();
		updateBackground();
	}
	
	public final void setSource(ElementData data) {
		this.data = data;
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
		background.setPosition(getX(), getY());
		background.setSize(getWidth(), getHeight());
		background.setColor(backgroundColor);
		background.draw(batch, parentAlpha);
		
		// Icon
		icon.width = getWidth() * iconScale;
		icon.height = getHeight() * iconScale;
		icon.x = getX() + (getWidth() - icon.width) / 2;
		icon.y = getY() + (getHeight() - icon.height) / 2;
		icon.color = getColor();
		icon.rotation = 180f;
		icon.draw(batch, parentAlpha);
		
		// Amount
		if (data.getAmount() > -1) {
			amount.setText(String.valueOf(data.getAmount()));
			amount.setX(getX() + getWidth() - amount.getPrefWidth() - PADDING);
			amount.setY(getY() + PADDING);
			amount.draw(batch, parentAlpha);
		}
	}
}