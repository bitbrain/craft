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

package de.bitbrain.craft.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Handles icon management and loads them on time
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class IconManager {
	
	public static final int BUFFER = 10;
	
	private static Sprite loadingSprite;	
	private static IconManager instance;	
	
	static {
		instance = new IconManager();
		loadingSprite = new Sprite(new Texture(Gdx.files.internal("images/icons/ico_loading.png")));
	}
	
	private Map<String, Icon> icons;	
	private Map<String, Integer> references;	
	private Map<String, Texture> textures;
	
	private Queue<String> requests;
	
	private IconManager() { 
		icons = new HashMap<String, Icon>();
		references = new HashMap<String, Integer>();
		textures = new HashMap<String, Texture>();
		requests = new LinkedList<String>();
	}
	
	public void update() {
		for (int i = 0; i < BUFFER; ++i) {
			loadIcon(requests.poll());
		}
	}
	
	public Icon fetchIcon(String file) {
		
		if (icons.containsKey(file)) {
			references.put(file, references.get(file) + 1);
			return icons.get(file);
		} else {
			references.put(file, 1);
			requests.add(file);
			return icons.put(file, new Icon());
		}
	}
	
	public void freeIcon(String file) {
		
	}
	
	public void dispose() {
		loadingSprite.getTexture().dispose();
	}
	
	public static IconManager getInstance() {
		return instance;
	}
	
	
	private void loadIcon(String file) {
		
	}
	
	public static class Icon {
		
		public float scale = 1.0f;
		public float x, y, width, height;
		public Color color = Color.WHITE;
		
		private Sprite sprite;
		
		Icon() {
			setTexture(null);
		}
		
		void setTexture(Texture texture) {
			if (texture != null) {
				this.sprite = new Sprite(texture);
			} else {
				this.sprite = loadingSprite;
			}
		}
		
		public void draw(Batch batch, float alphaModulation) {
			sprite.setScale(scale);
			sprite.setBounds(x, y, width, height);
			sprite.setColor(color);
			
			sprite.draw(batch, alphaModulation);
		}
	}
}
