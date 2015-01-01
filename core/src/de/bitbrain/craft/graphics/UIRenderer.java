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

package de.bitbrain.craft.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.inject.Inject;

import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.InputEventProcessor;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.util.DragDropHandler;

/**
 * Manages rendering of UI elements
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class UIRenderer {
	
	private FrameBuffer buffer;
	
	protected InputEventProcessor baseStage, overlayStage;
	
	private Batch batch;
	
	@Inject
	private EventBus eventBus;
	
	@Inject
	private DragDropHandler ddHandler;
	
	private Sprite overlay;
	
	private UIMode mode = UIMode.NORMAL;
	
	public UIRenderer(int width, int height, Viewport viewport, Batch batch) {
		SharedInjector.get().injectMembers(this);
		this.batch = batch;
		buffer = new FrameBuffer(Format.RGBA8888, width, height, false);
		baseStage = new InputEventProcessor(viewport, batch);
		overlayStage = new InputEventProcessor(viewport, batch);
		eventBus.subscribe(baseStage);
		eventBus.subscribe(overlayStage);
	}
	
	public Stage getBase() {
		return baseStage;
	}
	
	public Stage getOverlay() {
		return overlayStage;
	}
	
	public void setMode(UIMode mode) {
		this.mode = mode;		
		switch (mode) {
		case OVERLAY:
			Gdx.input.setInputProcessor(overlayStage);
			break;
		case NORMAL: default:
			Gdx.input.setInputProcessor(baseStage);
			break;
		}
	}
	
	public UIMode getMode() {
		return mode;
	}
	
	public void resize(int width, int height) {
		buffer.dispose();
		buffer = new FrameBuffer(Format.RGBA8888, width, height, false);
		baseStage.getViewport().update(width, height, true);
		overlayStage.getViewport().update(width, height, true);
	}

	public void render(float delta) {
		baseStage.act(delta);
		if (isOverlayMode()) {
			overlayStage.act(delta);
			buffer.begin();
		}
		baseStage.draw();
		batch.begin();
		ddHandler.draw(batch, delta);
		batch.end();
		if (isOverlayMode()) {
			buffer.end();
		}		
		if (isOverlayMode()) {
			if (overlay == null) {
				overlay = new Sprite(GraphicsFactory.createTexture(16, 16, Color.BLACK));
			}
			batch.begin();
			batch.draw(buffer.getColorBufferTexture(), 0, 0, 
					buffer.getWidth(), buffer.getHeight(),
					0, 0, buffer.getWidth(), buffer.getHeight(), false, true);
			overlay.setAlpha(0.3f);
			overlay.setBounds(0, 0, buffer.getWidth(), buffer.getHeight());
			overlay.draw(batch);
			batch.end();
			overlayStage.draw();
		}
	}

	private boolean isOverlayMode() {
		return mode.equals(UIMode.OVERLAY);
	}

	public void dispose() {
		eventBus.unsubscribe(baseStage);
		eventBus.unsubscribe(overlayStage);
		buffer.dispose();
	}
	
	public static enum UIMode {
		NORMAL,
		OVERLAY;
	}
}
