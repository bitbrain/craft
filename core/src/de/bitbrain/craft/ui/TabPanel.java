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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.audio.SoundUtils;
import de.bitbrain.craft.core.IconManager;
import de.bitbrain.craft.core.IconManager.Icon;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.tweens.ActorTween;

/**
 * Provides a panel with a tabbed menu at the bottom
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class TabPanel extends Table {
	
	private static final float HEIGHT_DIV = 1.2f;
	
	private Cell<?> content, menu;
	
	private Map<String, Actor> tabs;
	
	private TabControl tabControl;
	
	private Sprite background;
	
	private Set<TabListener> listeners;
	
	@Inject
	private IconManager iconManager;
	
	@Inject
	private TweenManager tweenManager;
	
	@Inject 
	private EventBus eventBus;

	@PostConstruct
	public void init() {
		listeners = new HashSet<TabListener>();
		tabs = new HashMap<String, Actor>();
		content = add();
		this.row();
		tabControl = new TabControl(this);
		menu = add(tabControl);		
		background = new Sprite(SharedAssetManager.get(Assets.TEX_PANEL_MEDIUM_BOX, Texture.class));
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
	
	public void addTab(String id, String iconId, Actor actor) {
		tabs.remove(id);		
		tabs.put(id, actor);
		tabControl.addTab(id, iconId);
		setTab(id);
		actor.getColor().a = 0f;
	}
	
	public void addListener(TabListener listener) {
		listeners.add(listener);
	}
	
	public void setTab(String id) {
		Actor actor = tabs.get(id);		
		if (actor != null) {			
			Actor current = content.getActor();			
			if (actor != current) {
				for (TabListener l : listeners) {
					l.onChange(current, actor);
				}
			}			
			if (current != null) {
				current.getColor().a = 0f;				
				tweenManager.killTarget(current);
			}			
			content.setActor(actor);
			tabControl.setTab(id);
			Tween.to(actor, ActorTween.ALPHA, 0.5f)
				.target(1f)
				.ease(TweenEquations.easeOutQuad)
				.start(tweenManager);
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
	
	public static interface TabListener {
		
		void onChange(Actor before, Actor next);
	}
	
	public class TabControl extends Table {
		
		private Map<ImageButton, String> buttons;
		private Map<String, ImageButton> ids;
		private Map<ImageButton, ImageButtonStyle> styles;
		private Map<ImageButton, ImageButtonStyle> activeStyles;
		private Map<ImageButton, Cell<?>> cells;
		
		private TabPanel parentPanel;		
		private ImageButton active;
		
		public TabControl(TabPanel panel) {
			this.parentPanel = panel;
			buttons = new HashMap<ImageButton, String>();
			ids = new HashMap<String, ImageButton>();
			cells = new HashMap<ImageButton, Cell<?>>();	
			styles = new HashMap<ImageButton, ImageButton.ImageButtonStyle>();
			activeStyles = new HashMap<ImageButton, ImageButton.ImageButtonStyle>();
		}
		
		public boolean isActive(String id) {
			return ids.get(id).equals(active);
		}
		
		public void addTab(String id, String iconId) {
			
			ImageButtonStyle style = generateStyle(iconId, false);
			ImageButtonStyle activeStyle = generateStyle(iconId, true);
			
			final ImageButton button = new ImageButton(style);
			final TabControl control = this;
			button.padBottom(15f);
			
			buttons.put(button, id);
			cells.put(button, add(button));
			ids.put(id, button);
			
			styles.put(button, style);
			activeStyles.put(button, activeStyle);
			
			setTab(button);
			
			button.addCaptureListener(new ClickListener() {
				/* (non-Javadoc)
				 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
				 */
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					
					SoundUtils.play(Assets.SND_TAB, 0.7f, 1.4f);
					 
					Actor a = event.getTarget();
					
					if (a instanceof Image) {
						a = a.getParent();
					}
					
					if (a instanceof ImageButton) {
						
						ImageButton b = (ImageButton)a;
						
						if (!isActive(buttons.get(b))) {							
							eventBus.fireEvent(new MouseEvent<TabControl>(EventType.CLICK, control, x, y));
							parentPanel.setTab(buttons.get(b));
						}					
					}
					
					
				}
			});
			
			for (Cell<?> c : cells.values()) {
				c.width((parentPanel.getWidth()) / cells.size());
				c.padBottom(5f);
				c.padRight(5f);
				c.padLeft(5f);
				c.height(parentPanel.getHeight() - (parentPanel.getHeight() / HEIGHT_DIV) - 15f);
			}
		}
		
		void setTab(String id) {
			setTab(ids.get(id));
		}
		
		private void setTab(ImageButton tab) {
			
			if (active != null) {
				active.setStyle(styles.get(active));
				cells.get(active).padBottom(0f);
			}
			active = tab;
			active.setStyle(activeStyles.get(active));
		}
		
		private ImageButtonStyle generateStyle(String iconId, boolean active) {
			
			ImageButtonStyle origin = Styles.BTN_TAB;
			
			if (active) {
				origin = Styles.BTN_TAB_ACTIVE;
			}
			
			ImageButtonStyle style = new ImageButtonStyle(origin);
			Icon icon = iconManager.fetch(iconId);
			icon.color.a = 0.5f;
			style.imageUp = icon;		
			style.imageOver = icon;
			
			style.imageUp.setMinHeight(Gdx.graphics.getHeight() / 10f);
			style.imageUp.setMinWidth(Gdx.graphics.getHeight() / 10f);
			style.imageOver.setMinHeight(Gdx.graphics.getHeight() / 10f);
			style.imageOver.setMinWidth(Gdx.graphics.getHeight() / 10f);
			return style;
		}
	}
}