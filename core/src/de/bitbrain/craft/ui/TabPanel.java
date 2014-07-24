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

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;

/**
 * Provides a panel with a tabbed menu at the bottom
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class TabPanel extends Table {
	
	private static final float HEIGHT_DIV = 1.2f;
	
	private Cell<?> content, menu;
	
	private Map<String, Actor> tabs;
	
	private TabControl tabControl;
	
	private Sprite background;
	
	private TweenManager tweenManager;

	public TabPanel(TweenManager tweenManager) {	
		this.tweenManager = tweenManager;
		tabs = new HashMap<String, Actor>();
		content = add();
		this.row();
		tabControl = new TabControl(this);
		menu = add(tabControl);
		
		background = new Sprite(SharedAssetManager.get(Assets.TEX_PANEL_MEDIUM_BOX, Texture.class));
		
		Button b = new TextButton("Blubb", Styles.BTN_GREEN);

		b.setBounds(0, 0, 10f, 10f);
		content.pad(Gdx.graphics.getHeight() / 30f);
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#setHeight(float)
	 */
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		content.height(height / HEIGHT_DIV);
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
	
	public void addTab(String id, Actor actor) {
		tabs.remove(id);		
		tabs.put(id, actor);
		tabControl.addTab(id);
		setTab(id);
	}
	
	public void setTab(String id) {
		Actor actor = tabs.get(id);
		
		if (actor != null) {
			content.setActor(actor);
			tabControl.setTab(id);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.ui.Table#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		background.setBounds(
				getX() + getPadLeft() / 2f - content.getPadLeft(), 
				getY() + content.getActorY() - content.getPadBottom(), 
				getWidth() + content.getPadRight() * 2, 
				content.getPrefHeight() + content.getPadTop() * 2);
		background.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
	
	public static class TabStyle {
		
		public String text;		
		public Sprite icon;
	}
	
	private static class TabControl extends Table {
		
		private Map<ImageButton, String> buttons;
		private Map<String, ImageButton> ids;
		private Map<ImageButton, Cell<?>> cells;
		
		private TabPanel parentPanel;
		
		private ImageButton active;
		
		public TabControl(TabPanel panel) {
			this.parentPanel = panel;
			buttons = new HashMap<ImageButton, String>();
			ids = new HashMap<String, ImageButton>();
			cells = new HashMap<ImageButton, Cell<?>>();			
		}
		
		public void addTab(String id) {
			final ImageButton button = new ImageButton(Styles.BTN_TAB);
			buttons.put(button, id);
			cells.put(button, add(button));
			ids.put(id, button);
			
			setTab(button);
			
			button.addCaptureListener(new ClickListener() {
				/* (non-Javadoc)
				 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
				 */
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					Actor a = event.getTarget();
					
					if (a instanceof ImageButton) {
						parentPanel.setTab(buttons.get((ImageButton)a));
					}
				}
			});
			
			for (Cell<?> c : cells.values()) {
				c.width((parentPanel.getWidth() / 1.2f) / cells.size());
				c.padRight(4f);
				c.padLeft(4f);
				c.height(parentPanel.getHeight() - (parentPanel.getHeight() / HEIGHT_DIV));
			}
		}
		
		void setTab(String id) {
			setTab(ids.get(id));
		}
		
		private void setTab(ImageButton tab) {
			
			if (active != null) {
				active.setStyle(Styles.BTN_TAB);
				cells.get(active).padBottom(0f);
			}
			active = tab;
			active.setStyle(Styles.BTN_TAB_ACTIVE);
			cells.get(active).padBottom(Gdx.graphics.getHeight() / 21.2f);
		}
	}
}





