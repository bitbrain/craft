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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
	
	public static final TextButtonStyle BTN_GREEN 			  = new TextButtonStyle();
	public static final TextButtonStyle BTN_PROFESSION     = new TextButtonStyle();
	public static final LabelStyle LBL_BROWN 		  = new LabelStyle();
	public static final ImageButtonStyle BTN_TAB				  = new ImageButtonStyle();
	public static final ImageButtonStyle BTN_TAB_ACTIVE 		  = new ImageButtonStyle();
	static void load() {
		BTN_GREEN.font = SharedAssetManager.get(Assets.FNT_LARGER, BitmapFont.class);
		BTN_GREEN.down = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEX_BUTTON_GREEN, Texture.class)));
		BTN_GREEN.up = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEX_BUTTON_GREEN_DARK, Texture.class)));
		BTN_GREEN.fontColor = Assets.CLR_GREEN_GRASS;
		BTN_GREEN.downFontColor = Assets.CLR_GREEN_GRASS_LIGHT;
		
		BTN_PROFESSION.font = SharedAssetManager.get(Assets.FNT_LARGER, BitmapFont.class);
		BTN_PROFESSION.down = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEX_PANEL_DOWN_V, Texture.class)));
		BTN_PROFESSION.over = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEX_PANEL_HOVER_V, Texture.class)));
		BTN_PROFESSION.up = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEX_PANEL_V, Texture.class)));
		BTN_PROFESSION.fontColor = Assets.CLR_YELLOW_SAND_A;
		BTN_PROFESSION.downFontColor = Assets.CLR_YELLOW_SAND;
		BTN_PROFESSION.overFontColor = Assets.CLR_YELLOW_SAND;
		
		LBL_BROWN.fontColor = Assets.CLR_BROWN_TEAK;
		LBL_BROWN.font =  SharedAssetManager.get(Assets.FNT_SMALL, BitmapFont.class);
		
		BTN_TAB.up = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEX_PANEL_TAB, Texture.class)));
		BTN_TAB_ACTIVE.up = new SpriteDrawable(new Sprite(SharedAssetManager.get(Assets.TEX_PANEL_TAB_ACTIVE, Texture.class)));
		
	}
}
