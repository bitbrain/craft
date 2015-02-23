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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import de.bitbrain.craft.graphics.GraphicsFactory;

/**
 * Styles file
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class Styles {

	public static final TextButtonStyle BTN_GREEN = new TextButtonStyle();
	public static final TextButtonStyle BTN_PROFESSION = new TextButtonStyle();
	public static final LabelStyle LBL_BROWN = new LabelStyle();
	public static final LabelStyle LBL_ITEM = new LabelStyle();
	public static final LabelStyle LBL_TEXT = new LabelStyle();
	public static final ImageButtonStyle BTN_TAB = new ImageButtonStyle();
	public static final ImageButtonStyle BTN_TAB_ACTIVE = new ImageButtonStyle();
	public static final TextFieldStyle TXT_COMMANDLINE = new TextFieldStyle();
	public static final TextButtonStyle BTN_RED = new TextButtonStyle();
	public static final LabelStyle LBL_CAPTION =  new LabelStyle();
	public static final LabelStyle LBL_TOOLTIP =  new LabelStyle();

	public static void load() {
		BTN_GREEN.font = SharedAssetManager.get(Assets.FNT_LARGER,
				BitmapFont.class);
		BTN_GREEN.down = new SpriteDrawable(new Sprite(SharedAssetManager.get(
				Assets.TEX_BUTTON_GREEN, Texture.class)));
		BTN_GREEN.up = new SpriteDrawable(new Sprite(SharedAssetManager.get(
				Assets.TEX_BUTTON_GREEN_DARK, Texture.class)));
		BTN_GREEN.fontColor = Assets.CLR_GREEN_GRASS;
		BTN_GREEN.downFontColor = Assets.CLR_GREEN_GRASS_LIGHT;
		
		BTN_RED.font = SharedAssetManager.get(Assets.FNT_LARGER,
				BitmapFont.class);
		BTN_RED.down = new SpriteDrawable(new Sprite(SharedAssetManager.get(
				Assets.TEX_BUTTON_RED, Texture.class)));
		BTN_RED.up = new SpriteDrawable(new Sprite(SharedAssetManager.get(
				Assets.TEX_BUTTON_RED_DARK, Texture.class)));
		BTN_RED.fontColor = Assets.CLR_RED;
		BTN_RED.downFontColor = Assets.CLR_RED_LIGHT;

		BTN_PROFESSION.font = SharedAssetManager.get(Assets.FNT_LARGER,
				BitmapFont.class);
		BTN_PROFESSION.down = new NinePatchDrawable(GraphicsFactory.createNinePatch(
				Assets.TEX_PANEL_HIGHLIGHT_9patch, Sizes.panelRadius()));
		BTN_PROFESSION.over = new NinePatchDrawable(GraphicsFactory.createNinePatch(
				Assets.TEX_PANEL_HIGHLIGHT_9patch, Sizes.panelRadius()));
		BTN_PROFESSION.up = new NinePatchDrawable(GraphicsFactory.createNinePatch(
				Assets.TEX_PANEL_9patch, Sizes.panelRadius()));
		BTN_PROFESSION.fontColor = Assets.CLR_INACTIVE;
		BTN_PROFESSION.downFontColor = Assets.CLR_YELLOW_SAND;
		BTN_PROFESSION.overFontColor = Assets.CLR_YELLOW_SAND;

		LBL_BROWN.fontColor = Assets.CLR_BROWN_TEAK;
		LBL_BROWN.font = SharedAssetManager.get(Assets.FNT_SMALL,
				BitmapFont.class);

		LBL_ITEM.fontColor = new Color(Color.WHITE);
		LBL_ITEM.font = SharedAssetManager.get(Assets.FNT_LARGER,
				BitmapFont.class);

		LBL_TEXT.fontColor = new Color(Color.WHITE);
		LBL_TEXT.font = SharedAssetManager.get(Assets.FNT_MEDIUM,
				BitmapFont.class);
		
		LBL_CAPTION.fontColor = Assets.CLR_YELLOW_SAND;
		LBL_CAPTION.font = SharedAssetManager.get(Assets.FNT_LARGER,
				BitmapFont.class);
		
		LBL_TOOLTIP.fontColor = new Color(Color.WHITE);
		LBL_TOOLTIP.font =  SharedAssetManager.get(Assets.FNT_SMALLEST,
				BitmapFont.class);

		BTN_TAB.up = new NinePatchDrawable(GraphicsFactory.createNinePatch(
				Assets.TEX_PANEL_TAB_9patch, Sizes.panelRadius()));
		BTN_TAB_ACTIVE.up = new NinePatchDrawable(GraphicsFactory.createNinePatch(
				Assets.TEX_PANEL_TAB_ACTIVE_9patch, Sizes.panelRadius()));
		TXT_COMMANDLINE.font = SharedAssetManager.get(Assets.FNT_MONO,
				BitmapFont.class);
		TXT_COMMANDLINE.fontColor = Color.GRAY;
		TXT_COMMANDLINE.messageFont =  SharedAssetManager.get(Assets.FNT_MONO,
				BitmapFont.class);
		TXT_COMMANDLINE.messageFontColor = Color.WHITE;
		TXT_COMMANDLINE.cursor = new SpriteDrawable(new Sprite(SharedAssetManager.get(
				Assets.TEX_CURSOR, Texture.class)));
		TXT_COMMANDLINE.focusedFontColor = Color.WHITE;

	}
}
