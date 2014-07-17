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

package de.bitbrain.craft;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Styles file
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class Styles {
	
	public static final TextButtonStyle TEXT_BUTTON = new TextButtonStyle();
	public static final TextButtonStyle PROFESSION_BUTTON = new TextButtonStyle();

	static void load() {
		TEXT_BUTTON.font = SharedAssetManager.get(Assets.FONT_LARGER, BitmapFont.class);
		TEXT_BUTTON.down = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEXTURE_BUTTON, Texture.class)));
		TEXT_BUTTON.up = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEXTURE_BUTTON_DARK, Texture.class)));
		TEXT_BUTTON.fontColor = Assets.COLOR_GREEN_GRASS;
		TEXT_BUTTON.downFontColor = Assets.COLOR_GREEN_GRASS_LIGHT;
		
		PROFESSION_BUTTON.font = SharedAssetManager.get(Assets.FONT_LARGER, BitmapFont.class);
		PROFESSION_BUTTON.down = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEXTURE_BUTTON, Texture.class)));
		PROFESSION_BUTTON.up = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEXTURE_BUTTON_DARK, Texture.class)));
		PROFESSION_BUTTON.fontColor = Assets.COLOR_WOOD_TEAK;
		PROFESSION_BUTTON.downFontColor = Assets.COLOR_WOOD_TEAK;
		
	}
}
