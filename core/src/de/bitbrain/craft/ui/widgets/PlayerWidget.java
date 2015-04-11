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

package de.bitbrain.craft.ui.widgets;

import net.engio.mbassy.listener.Handler;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.ProgressEvent;
import de.bitbrain.craft.graphics.GraphicsFactory;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.models.Progress;
import de.bitbrain.craft.tweens.FadeableTween;
import de.bitbrain.craft.tweens.FloatValueTween;
import de.bitbrain.craft.util.Fadeable;
import de.bitbrain.craft.util.FloatValueProvider;

/**
 * Shows information of a player like level and experience
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class PlayerWidget extends Actor implements Fadeable {
	
	private float padding = 15f;

	@Inject
	private EventBus eventBus;

	@Inject
	private TweenManager tweenManager;

	@Inject
	private API api;

	private NinePatch background1, background2;

	private BitmapFont caption;

	private Progress progress;

	private Profession profession;

	private PlayerWidgetTextProvider textProvider;

	private FloatValueProvider progressProvider;

	public PlayerWidget(Profession profession) {
		SharedInjector.get().injectMembers(this);
		progressProvider = new FloatValueProvider();
		textProvider = new DefaultTextProvider();
		this.profession = profession;
		eventBus.subscribe(this);
		this.progress = api.getProgress(profession);
		setProgress(progress);
		background1 = GraphicsFactory.createNinePatch(
				Assets.TEX_PANEL_TRANSPARENT_9patch, 5);
		background2 = GraphicsFactory.createNinePatch(
				Assets.TEX_PANEL_BAR_9patch, 5);
		caption = SharedAssetManager.get(Assets.FNT_SMALL, BitmapFont.class);
		setColor(new Color(Assets.CLR_YELLOW_SAND));
	}
	
	public void setPadding(float padding) {
		this.padding = padding;
	}

	public void setTextProvider(PlayerWidgetTextProvider provider) {
		this.textProvider = provider;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		drawBackground(batch);
		drawProgress(batch);
		drawForeground(batch);
	}

	private void drawBackground(Batch batch) {
		background1.setColor(getColor());
		background1.draw(batch, getX(), getY(), getWidth(), getHeight());
		background1.draw(batch, getX() + padding, getY() + padding, getWidth() - padding * 2, getHeight() - padding * 2);
	}

	private void drawProgress(Batch batch) {
		if (progressProvider.getValue() > 0.05f) {
			background2.setColor(getColor());
			background2.draw(batch, getX() + padding, getY() + padding, (getWidth()
					* progressProvider.getValue()) - padding * 2, getHeight() - padding * 2);
		}
	}

	private void drawForeground(Batch batch) {
		String text = textProvider.getText(progress, profession);
		caption.setColor(getColor());
		caption.getColor().a -= 0.2f;
		caption.draw(batch, text,
				getX() + getWidth() / 2f - caption.getBounds(text).width / 2f,
				getY() + getHeight() / 2f + caption.getLineHeight() / 2f);
		caption.getColor().a += 0.2f;
	}

	@Override
	public float getAlpha() {
		return getColor().a;
	}

	@Override
	public void setAlpha(float alpha) {
		getColor().a = alpha;
	}

	@Handler
	private void onProgressUpdated(ProgressEvent event) {
		Progress progress = event.getModel();
		if (progress.getProfession().equals(profession)) {
			setProgress(progress);
		}
	}

	private void setProgress(final Progress progress) {
		tweenManager.killTarget(this);
		tweenManager.killTarget(progressProvider);
		final float oldProgress = this.progress.getCurrentProgress();
		float duration = 1f;
		duration = progress.getLevel() == this.progress.getLevel() ? 1f
				: (1.3f - oldProgress);
		final Tween tween = Tween
				.to(progressProvider, FloatValueTween.VALUE, duration)
				.ease(TweenEquations.easeOutQuad)				
				.setCallbackTriggers(TweenCallback.COMPLETE);
		if (progress.getLevel() == this.progress.getLevel()) {
			tween.target(progress.getCurrentProgress());
		} else if (progress.getLevel() > this.progress.getLevel()) {
			tween.target(1f);
			tween.ease(TweenEquations.easeNone);
			tween.setCallback(new TweenCallback() {
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					progressProvider.setValue(0f);
					Tween.to(progressProvider, FloatValueTween.VALUE,
							oldProgress * 1f)
							.target(progress.getCurrentProgress())
							.ease(TweenEquations.easeOutQuad)
							.start(tweenManager);
				}
			});
		}

		tween.start(tweenManager);
		this.progress = progress;
	}

	public static interface PlayerWidgetTextProvider {
		String getText(Progress progress, Profession profession);
	}

	public static class DefaultTextProvider implements PlayerWidgetTextProvider {

		@Override
		public String getText(Progress progress, Profession profession) {
			String text = "Level ";
			text += progress.getLevel() + " ";
			text += profession.getName();
			return text;
		}
	}

	public static class LevelTextProvider implements PlayerWidgetTextProvider {

		@Override
		public String getText(Progress progress, Profession profession) {
			if (progress.getXp() == 0) {
				return "Start";
			} else {
				return String.valueOf(progress.getLevel());
			}
		}
	}
}
