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

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Provides a panel with a tabbed menu at the bottom
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class TabPanel extends Table {
	
	private Cell<?> content;
	
	private Map<String, Actor> tabs;

	public TabPanel() {	
		tabs = new HashMap<String, Actor>();
		content = add();
		this.row();
	}
	
	public void addTab(String id, TabStyle tabStyle, Actor actor) {
		tabs.remove(id);		
		tabs.put(id, actor);
	}
	
	public void setTab(String id) {
		Actor actor = tabs.get(id);
		
		if (actor != null) {
			content.setActor(actor);
		}
	}
	
	public static class TabStyle {
		
		public String text;		
		public Sprite icon;
	}
}
