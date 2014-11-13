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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.audio.SoundUtils;
import de.bitbrain.craft.core.Icon;
import de.bitbrain.craft.core.IconManager;
import de.bitbrain.craft.core.IconManager.IconDrawable;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.PostConstruct;
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
		align(Align.top);
		listeners = new HashSet<TabListener>();
		tabs = new HashMap<String, Actor>();
		content = add().align(Align.left | Align.top).pad(Sizes.tabPadding());
		tabControl = new TabControl(this);
		menu = add(tabControl);		
		background = new Sprite(SharedAssetManager.get(Assets.TEX_PANEL_MEDIUM_BOX, Texture.class));
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#setWidth(float)
	 */
	@Override
	public void setWidth(float width) {
		content.width(width);
		super.setWidth(width + menu.getActorWidth());
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#setHeight(float)
	 */
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		content.height(height);
		menu.height(height);
	}
	
	public void addTab(String id, Icon icon, Actor actor) {
		tabs.remove(id);		
		tabs.put(id, actor);
		tabControl.addTab(id, icon);
		setTab(id);
		actor.getColor().a = 0f;
		this.invalidateHierarchy();
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
				content.getActorX(),
				content.getActorY(),
				content.getActorWidth() + content.getPadRight(),
				content.getActorHeight() + content.getPadTop());
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
			align(Align.top);
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
		
		public void addTab(String id, Icon icon) {
			
			ImageButtonStyle style = generateStyle(icon, false);
			ImageButtonStyle activeStyle = generateStyle(icon, true);
			
			final ImageButton button = new ImageButton(style);
			final TabControl control = this;
			button.padBottom(15f);
			
			buttons.put(button, id);
			cells.put(button, add(button).row().padTop(10f));
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
		}
		
		void setTab(String id) {
			setTab(ids.get(id));
		}
		
		private void setTab(ImageButton tab) {
			
			if (active != null) {
				active.setStyle(styles.get(active));
			}
			active = tab;
			active.setStyle(activeStyles.get(active));
		}
		
		private ImageButtonStyle generateStyle(Icon icon, boolean active) {
			
			ImageButtonStyle origin = Styles.BTN_TAB;
			
			if (active) {
				origin = Styles.BTN_TAB_ACTIVE;
			}
			
			ImageButtonStyle style = new ImageButtonStyle(origin);
			IconDrawable iconDrawable = iconManager.fetch(icon);
			iconDrawable.color.a = 0.5f;
			style.imageUp = iconDrawable;		
			style.imageOver = iconDrawable;
			
			style.imageUp.setMinHeight(Gdx.graphics.getHeight() / 10f);
			style.imageUp.setMinWidth(Gdx.graphics.getHeight() / 10f);
			style.imageOver.setMinHeight(Gdx.graphics.getHeight() / 10f);
			style.imageOver.setMinWidth(Gdx.graphics.getHeight() / 10f);
			return style;
		}
	}
}