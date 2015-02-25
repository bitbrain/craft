/*
 * Craft - Crafting game for Android, PC and Browser.
 * Copyright (C) 2014 Miguel Gonzalez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *ch 
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

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.graphics.GraphicsFactory;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.tweens.FadeableTween;
import de.bitbrain.craft.util.Fadeable;


/**
 * Tooltip which will be displayed over an actor
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Tooltip extends InputListener implements Poolable, Fadeable {
	
	private static final float OFFSET = 20f;
	
	private String text;
	
	@Inject
	private TooltipManager manager;
	
	@Inject
	private TweenManager tweenManager;
	
	private Actor target;
	
	private NinePatch background;
	
	private BitmapFont font;
	
	private float alpha = 0f;
	
	static {
		Tween.registerAccessor(Tooltip.class, new FadeableTween());
	}

	public static Tooltip create(Actor target) {
		Tooltip tooltip = new Tooltip();
		SharedInjector.get().injectMembers(tooltip);
		tooltip.init();
		tooltip.target(target);
		return tooltip;
	}
	
	/* private constructor */
	private Tooltip() { }
	
	private void init() {
		manager.register(this);
		font = SharedAssetManager.get(Assets.FNT_MEDIUM, BitmapFont.class);
		background = GraphicsFactory.createNinePatch(Assets.TEX_PANEL_BLACK_9patch, Sizes.panelTransparentRadius());
	}
	
	public Tooltip text(String text) {
		this.text = text;
		return this;
	}
	
	public Tooltip target(Actor target) {
		if (this.target != null) {
			this.target.removeCaptureListener(this);
		}
		this.target = target;
		this.target.addCaptureListener(this);
		return this;
	}
	
	void draw(Batch batch, float parentAlpha) {
		if (target != null && text != null) {
			final float PADDING = 10f;
			TextBounds bounds = font.getBounds(text);
			float x = (Gdx.input.getX() / Sizes.worldScreenFactorX()) + OFFSET;
			float y = ((Gdx.graphics.getHeight() - Gdx.input.getY()) / Sizes.worldScreenFactorY()) - OFFSET;
			float width = bounds.width + PADDING * 2; float height = font.getLineHeight() * 2;
			background.setColor(new Color(1f, 1f, 1f, alpha));
			background.draw(batch, x, y, width, height);
			font.setColor(1f, 1f, 1f, alpha);
			font.draw(batch, text, x + PADDING, y + height - PADDING - 3f);
		}
	}
	
	@Override
	public void enter(InputEvent event, float x, float y, int pointer,
			Actor fromActor) {
		animateFadeIn();
	}
	
	@Override
	public void exit(InputEvent event, float x, float y, int pointer,
			Actor toActor) {
		super.exit(event, x, y, pointer, toActor);
		animateFadeOut();
	}

	@Override
	public void reset() {
		this.text = "";
		this.target = null;
		manager.unregister(this);
	}
	
	private void animateFadeIn() {
		tweenManager.killTarget(this);
		Tween.to(this, FadeableTween.DEFAULT, 0.2f)
			 .target(1f)
			 .ease(TweenEquations.easeInQuad)
			 .start(tweenManager);
	}
	
	private void animateFadeOut() {
		tweenManager.killTarget(this);
		Tween.to(this, FadeableTween.DEFAULT, 0.1f)
			 .target(0f)
			 .ease(TweenEquations.easeInQuad)
			 .start(tweenManager);
	}

	@Override
	public float getAlpha() {
		return alpha;
	}

	@Override
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

}
