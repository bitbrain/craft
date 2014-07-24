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

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;

/**
 * Provides a panel with a tabbed menu at the bottom
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class TabPanel extends Table {
	
	private Cell<?> content, menu;
	
	private Map<String, Actor> tabs;
	
	private TabControl tabControl;
	
	private Sprite background;

	public TabPanel() {	
		tabs = new HashMap<String, Actor>();
		content = add();
		this.row();
		tabControl = new TabControl();
		menu = add(tabControl);
		
		background = new Sprite(SharedAssetManager.get(Assets.TEX_PANEL_V, Texture.class));
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#setHeight(float)
	 */
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		content.height(height / 1.2f);
		menu.height(height - content.getPrefHeight());
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#setWidth(float)
	 */
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		content.width(width);
		menu.width(width);
	}
	
	public void addTab(String id, TabStyle tabStyle, Actor actor) {
		tabs.remove(id);		
		tabs.put(id, actor);
		tabControl.addTab(id);
	}
	
	public void setTab(String id) {
		Actor actor = tabs.get(id);
		
		if (actor != null) {
			content.setActor(actor);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.ui.Table#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		background.setBounds(
				getX() + getPadLeft() / 2f, 
				getY() + content.getActorY(), getWidth(), content.getPrefHeight());
		background.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
	
	public static class TabStyle {
		
		public String text;		
		public Sprite icon;
	}
	
	private static class TabControl extends Table {
		
		private Map<ImageButton, String> buttons;
		
		public TabControl() {
			buttons = new HashMap<ImageButton, String>();
		}
		
		public void addTab(String id) {
			
		}
	}
}
