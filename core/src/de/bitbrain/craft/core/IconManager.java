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
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;

import de.bitbrain.craft.Assets;

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
		loadingSprite.flip(false, true);
	}
	
	private Map<String, Icon> icons;	
	private Map<String, Integer> references;	
	private Map<String, Texture> textures;
	
	private Queue<String> requests;
	
	private float alpha = 1.0f;
	
	private IconManager() { 
		icons = new HashMap<String, Icon>();
		references = new HashMap<String, Integer>();
		textures = new HashMap<String, Texture>();
		requests = new LinkedList<String>();
	}
	
	public void update() {
		
		for (int i = 0; i < BUFFER; ++i) {
			
			if (requests.isEmpty()) {
				break;
			}
			
			loadIcon(requests.poll());
		}
	}
	
	public Icon fetch(String file) {		
		if (icons.containsKey(file)) {
			return icons.get(file);
		} else {
			references.put(file, 1);
			requests.add(file);
			Icon icon = new Icon();
			icons.put(file, icon);
			return icon;
		}
	}
	
	public void free(String file) {
		if (references.containsKey(file)) {
			if (references.get(file) > 1) {
				references.put(file, references.get(file) - 1);
			} else {
				references.remove(file);
				textures.get(file).dispose();
				textures.remove(file);
				icons.get(file).setTexture(null);
			}
		}
	}
	
	public void setAlpha(float alpha) {
		for (Icon icon : icons.values()) {
			icon.color.a = alpha;
		}
		this.alpha = alpha;
	}
	
	public float getAlpha() {
		return alpha;
		
	}
	
	public void dispose() {
		
		for (Texture t : textures.values()) {
			t.dispose();
		}
		
		references.clear();
		icons.clear();
		textures.clear();
	}
	
	public static IconManager getInstance() {
		return instance;
	}
	
	
	private void loadIcon(String file) {
		if (!textures.containsKey(file)) {
			Texture texture = new Texture(Gdx.files.internal(Assets.DIR_ICONS + file));
			textures.put(file, texture);
			icons.get(file).setTexture(texture);
		}
	}
	
	
	
	public static class Icon extends BaseDrawable implements TransformDrawable {
		
		public float scale = 1.0f;
		public float x, y, width, height;
		public float rotation;
		public Color color = new Color(Color.WHITE);
		
		private Sprite sprite;
		
		Icon() {
			setTexture(null);
		}
		
		void setTexture(Texture texture) {
			if (texture != null) {
				this.sprite = new Sprite(texture);
				sprite.flip(false, true);
			} else {
				this.sprite = loadingSprite;
			}
		}
		
		public void draw(Batch batch, float alphaModulation) {
			sprite.setScale(scale);
			sprite.setBounds(x, y, width, height);
			sprite.setColor(color);
			sprite.setOrigin(width / 2f, height / 2f);
			sprite.setRotation(rotation);
			
			sprite.draw(batch, alphaModulation);
		}

		/* (non-Javadoc)
		 * @see com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable#draw(com.badlogic.gdx.graphics.g2d.Batch, float, float, float, float, float, float, float, float, float)
		 */
		@SuppressWarnings("deprecation")
		@Override
		public void draw(Batch batch, float x, float y, float originX,
				float originY, float width, float height, float scaleX,
				float scaleY, float rotation) {
			sprite.setOrigin(originX, originY);
			sprite.setRotation(rotation);
			sprite.setScale(scaleX, scaleY);
			sprite.setBounds(x, y, width, height);
			Color color = sprite.getColor();
			sprite.setColor(Color.tmp.set(color).mul(batch.getColor()));
			sprite.draw(batch);
			sprite.setColor(color);
			
		}
		
		/* (non-Javadoc)
		 * @see com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable#draw(com.badlogic.gdx.graphics.g2d.Batch, float, float, float, float)
		 */
		@Override
		public void draw(Batch batch, float x, float y, float w,
				float h) {
			
			if (sprite.isFlipY()) {
				sprite.flip(false, true);
			}
			super.draw(batch, x, y, w, h);
			sprite.setScale(scale);
			sprite.setBounds(x, y, w, h);
			sprite.setColor(color);
			sprite.setOrigin(w / 2f, h / 2f);
			sprite.setRotation(rotation);
			
			sprite.draw(batch, color.a);
			
		}
		
		
	}
}
