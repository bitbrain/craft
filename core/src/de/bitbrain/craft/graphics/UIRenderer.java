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

import net.engio.mbassy.listener.Handler;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.inject.Inject;

import de.bitbrain.craft.animations.BlurShaderTween;
import de.bitbrain.craft.animations.SpriteTween;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.InputEventProcessor;
import de.bitbrain.craft.events.KeyEvent;
import de.bitbrain.craft.graphics.shader.ShadeArea;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.ui.cli.CommandLineInterface;
import de.bitbrain.craft.util.DragDropHandler;

/**
 * Manages rendering of UI elements
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class UIRenderer implements ShadeArea {

	private static final float OVERLAY_OPACITY = 0.5f;

	private static final float OVERLAY_FADE = 0.35f;

	private FrameBuffer buffer;

	protected InputEventProcessor baseStage, overlayStage, cliStage;

	private Batch batch;

	@Inject
	private EventBus eventBus;

	@Inject
	private DragDropHandler ddHandler;

	@Inject
	private CommandLineInterface cli;

	@Inject
	private TweenManager tweenManager;

	private Sprite overlay;

	private UIMode mode = UIMode.NORMAL;
	
	private GaussianBlur blurHandler;

	public UIRenderer(int width, int height, Viewport viewport, Batch batch) {
		SharedInjector.get().injectMembers(this);
		this.batch = batch;
		blurHandler = new GaussianBlur(this);
		buffer = new FrameBuffer(Format.RGBA8888, width, height, false);
		baseStage = new InputEventProcessor(viewport, batch);
		overlayStage = new InputEventProcessor(viewport, batch);
		cliStage = new InputEventProcessor(viewport, batch);
		cliStage.addActor(cli);
		eventBus.subscribe(baseStage);
		eventBus.subscribe(overlayStage);
		eventBus.subscribe(this);
		overlay = new Sprite(GraphicsFactory.createTexture(16, 16, Color.BLACK));
		overlay.setAlpha(0f);
	}

	public Stage getBase() {
		return baseStage;
	}

	public Stage getOverlay() {
		return overlayStage;
	}

	public void syncMode() {
		setMode(getMode());
	}

	public void setMode(UIMode mode) {
		switch (mode) {
		case OVERLAY:
			Gdx.input.setInputProcessor(overlayStage);
			if (this.mode != mode) {
				animateFadeIn();
			}
			break;
		case NORMAL:
		default:
			Gdx.input.setInputProcessor(baseStage);
			if (this.mode != mode) {
				animateFadeOut();
			}
			break;
		}
		this.mode = mode;
	}

	public UIMode getMode() {
		return mode;
	}

	public void resize(int width, int height) {
		buffer.dispose();
		buffer = new FrameBuffer(Format.RGBA8888, width, height, false);
		baseStage.getViewport().update(width, height, true);
		overlayStage.getViewport().update(width, height, true);
		cliStage.getViewport().update(width, height, true);
		blurHandler.resize(width, height);
	}

	@Handler
	public void keyEvent(KeyEvent event) {
		int key = event.getKey();
		if (key == Keys.F3 && event.getType() == EventType.KEYDOWN) {
			Gdx.input.setInputProcessor(cliStage);
			cli.setVisible(!cli.isVisible());
			if (cli.isVisible()) {
				cli.focus();
			} else {
				setMode(mode);
			}
		}
	}

	public void render(float delta) {
		baseStage.act(delta);
		cliStage.act(delta);
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
		blurHandler.updateAndRender(batch, delta);
		if (isOverlayMode()) {
			overlayStage.draw();
		}
		cliStage.draw();
	}

	private boolean isOverlayMode() {
		return mode.equals(UIMode.OVERLAY);
	}

	public void dispose() {
		eventBus.unsubscribe(baseStage);
		eventBus.unsubscribe(overlayStage);
		eventBus.unsubscribe(this);
		buffer.dispose();
		baseStage.dispose();
		overlayStage.dispose();
		cliStage.dispose();
	}

	public static enum UIMode {
		NORMAL, OVERLAY;
	}

	@Override
	public void draw(Batch batch, float delta) {
		if (isOverlayMode() || overlay.getColor().a > 0) {
			batch.draw(buffer.getColorBufferTexture(), 0, 0, buffer.getWidth(),
					buffer.getHeight(), 0, 0, buffer.getWidth(),
					buffer.getHeight(), false, true);
			overlay.setBounds(0, 0, buffer.getWidth(), buffer.getHeight());
			overlay.draw(batch);
		}
	}
	
	private void killAnimations() {
		tweenManager.killTarget(overlay);
		tweenManager.killTarget(blurHandler.getHorizontalBlur());
		tweenManager.killTarget(blurHandler.getVerticalBlur());
	}
	
	private void animateFadeIn() {
		killAnimations();
		Tween.to(overlay, SpriteTween.ALPHA, OVERLAY_FADE)
				.target(OVERLAY_OPACITY)
				.ease(TweenEquations.easeOutQuad).start(tweenManager);
		Tween.to(blurHandler.getVerticalBlur(), BlurShaderTween.SIZE, OVERLAY_FADE)
				.target(0.4f)
				.ease(TweenEquations.easeOutQuad).start(tweenManager);
		Tween.to(blurHandler.getHorizontalBlur(), BlurShaderTween.SIZE, OVERLAY_FADE)
				.target(0.4f)
				.ease(TweenEquations.easeOutQuad).start(tweenManager);
	}
	
	private void animateFadeOut() {
		killAnimations();
		Tween.to(overlay, SpriteTween.ALPHA, OVERLAY_FADE).target(0f)
				.ease(TweenEquations.easeOutQuad).start(tweenManager);
		blurHandler.getVerticalBlur().setBlurSize(0);
		blurHandler.getHorizontalBlur().setBlurSize(0);
	}
}
