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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.audio.SoundUtils;
import de.bitbrain.craft.graphics.GraphicsFactory;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.tweens.ActorTween;
import de.bitbrain.craft.tweens.SpriteTween;
import de.bitbrain.craft.util.PlayerDataProvider;

/**
 * This element shows a selection for all professions. It is also possible to
 * add a listener which detects, if a certain profession has been clicked (as an
 * enumeration)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ProfessionSelectionView extends Table implements EventListener {

	private Map<Cell<?>, ProfessionElement> elements = new HashMap<Cell<?>, ProfessionElement>();

	private List<ProfessionSelectListener> listeners;

	private PlayerDataProvider playerDataProvider;

	@Inject
	private TweenManager tweenManager;

	public ProfessionSelectionView(PlayerDataProvider playerDataProvider) {
		SharedInjector.get().injectMembers(this);
		listeners = new ArrayList<ProfessionSelectListener>();
		this.playerDataProvider = playerDataProvider;

		ClickNotifier notifier = new ClickNotifier();

		for (int index = 0; index < Profession.values().length; index++) {

			Profession profession = Profession.values()[index];
			String caption = profession.isEnabled() ? profession.getName()
					: "unknown";
			TextButtonStyle style = profession.isEnabled() ? Styles.BTN_PROFESSION
					: Styles.BTN_PROFESSION_INACTIVE;
			ProfessionElement element = new ProfessionElement(caption, style,
					profession);
			element.setDisabled(!profession.isEnabled());
			element.addCaptureListener(this);

			Cell<?> cell = add(element);
			elements.put(cell, element);
			if (profession.isEnabled()) {
				element.addCaptureListener(notifier);
				animateElement(index, element, tweenManager);
			}
		}

		this.pad(Sizes.worldWidth() / 20f);
		pack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.gdx.scenes.scene2d.EventListener#handle(com.badlogic.gdx
	 * .scenes.scene2d.Event)
	 */
	@Override
	public boolean handle(Event event) {

		if (event.isCapture()) {
			notify(event, true);
			return true;
		}

		return false;
	}

	public void addProfessionSelectListener(ProfessionSelectListener l) {
		this.listeners.add(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup#sizeChanged()
	 */
	@Override
	protected void sizeChanged() {
		super.sizeChanged();

		for (Entry<Cell<?>, ProfessionElement> entry : elements.entrySet()) {
			alignSize(entry.getKey(), entry.getValue());
		}

	}

	private void alignSize(Cell<?> cell, ProfessionElement element) {
		cell.width((Sizes.worldWidth() / 1.2f) / Profession.values().length)
				.height(Sizes.worldHeight() / 1.3f)
				.pad(Sizes.worldWidth() / 70f);
		element.getLabel().setFontScale(element.getWidth() / 270f);
		element.padTop(element.getHeight() / 2.3f);
	}

	private void animateElement(final int index,
			final ProfessionElement element, final TweenManager tweenManager) {
		element.getColor().a = 0f;
		element.getIcon().setScale(0f);
		TweenCallback callback = new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {

				float progress = playerDataProvider.getProgress(element
						.getProfession());

				if (progress < 0f) {
					progress = 0f;
				} else if (progress > 1.0f) {
					progress = 1f;
				}

				Tween.to(element.getBar(), 1, 0.8f).target(progress)
						.ease(TweenEquations.easeInOutQuad).start(tweenManager);

				Tween.to(element.getIcon(), SpriteTween.SCALE, 1.0f)
						.delay(0.3f)
						.setCallbackTriggers(TweenCallback.START)
						.setCallback(new TweenCallback() {
							@Override
							public void onEvent(int type, BaseTween<?> source) {
								Sound s = SharedAssetManager.get(
										Assets.SND_POP, Sound.class);
								s.play(1.0f, index / 9.6f + 0.7f, 0.3f);
							}
						}).target(1.0f).ease(TweenEquations.easeOutElastic)
						.start(tweenManager);
			}
		};

		Tween.to(element, ActorTween.ALPHA, 0.6f).delay(index / 5f)
				.target(1.0f).setCallback(callback)
				.setCallbackTriggers(TweenCallback.START)
				.ease(TweenEquations.easeInOutQuart).start(tweenManager);
	}

	public class ProfessionElement extends TextButton {

		private Sprite icon;

		private float iconAlpha = 0.5f;

		private Profession profession;

		private ProfessionBar bar;

		/**
		 * @param text
		 * @param skin
		 */
		public ProfessionElement(String text, TextButtonStyle style,
				Profession profession) {
			super(text, style);
			Tween.registerAccessor(ProfessionBar.class,
					new ProfessionBarTween());
			this.profession = profession;

			Texture tex = getProfessionTexture(profession);
			bar = new ProfessionBar();

			if (tex != null) {
				icon = new Sprite(tex);
			}
			if (profession.isEnabled()) {
				addCaptureListener(new IconModulator());
			}
		}

		public Profession getProfession() {
			return profession;
		}

		public Sprite getIcon() {
			return icon;
		}

		public ProfessionBar getBar() {
			return bar;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.badlogic.gdx.scenes.scene2d.ui.TextButton#draw(com.badlogic.gdx
		 * .graphics.g2d.Batch, float)
		 */
		@Override
		public void draw(Batch batch, float parentAlpha) {
			if (!profession.isEnabled()) {
				getColor().a = 0.3f;
			}
			super.draw(batch, parentAlpha);
			if (icon != null) {
				if (!profession.isEnabled()) {
					icon.setColor(Color.BLACK);
				}
				icon.setSize(getWidth() / 1.3f, getWidth() / 1.3f);
				icon.setPosition(getX() + getWidth() / 2 - icon.getWidth() / 2,
						getY() + getHeight() / 2.5f);
				icon.setOrigin(icon.getWidth() / 2f, icon.getHeight() / 2f);
				icon.draw(batch, parentAlpha * iconAlpha * getColor().a);
			}
			if (profession.isEnabled()) {
				bar.setColor(getColor());
				bar.setWidth(getWidth() / 1.4f);
				bar.setHeight(getHeight() / 11f);
				bar.setX(getX() + getWidth() / 2f - bar.getWidth() / 2f);
				bar.setY(getY() + getHeight() / 7.5f);
				bar.draw(batch, parentAlpha);
			}
		}

		private Texture getProfessionTexture(Profession profession) {
			if (profession.getIcon() != null) {
				return SharedAssetManager.get(profession.getIcon(),
						Texture.class);
			} else {
				return null;
			}
		}

		private class ProfessionBarTween implements
				TweenAccessor<ProfessionBar> {

			public static final int PROGRESS = 1;

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * aurelienribon.tweenengine.TweenAccessor#getValues(java.lang.Object
			 * , int, float[])
			 */
			@Override
			public int getValues(ProfessionBar target, int tweenType,
					float[] returnValues) {
				if (tweenType == PROGRESS) {
					returnValues[0] = target.progress;
					return 1;
				}
				return 0;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * aurelienribon.tweenengine.TweenAccessor#setValues(java.lang.Object
			 * , int, float[])
			 */
			@Override
			public void setValues(ProfessionBar target, int tweenType,
					float[] newValues) {
				if (tweenType == PROGRESS) {
					target.progress = newValues[0];
				}
			}
		}

		private class ProfessionBar extends Actor {

			private NinePatch background, foreground;

			private Label level;

			public float alpha = 0.5f;

			public float progress = 0.0f;

			public ProfessionBar() {
				background = GraphicsFactory.createNinePatch(
						Assets.TEX_PANEL_BAR_9patch,
						Sizes.panelTransparentRadius());
				foreground = GraphicsFactory.createNinePatch(
						Assets.TEX_PANEL_BAR_9patch,
						Sizes.panelTransparentRadius());
				LabelStyle lblStyle = new LabelStyle();
				lblStyle.font = SharedAssetManager.get(Assets.FNT_SMALL,
						BitmapFont.class);
				lblStyle.fontColor = Assets.CLR_BLUE_SKY;
				String text = String.valueOf(playerDataProvider
						.getLevel(profession));

				if (playerDataProvider.getLevel(profession) == 1
						&& playerDataProvider.getProgress(profession) == 0f) {
					text = "New game";
				}

				level = new Label(text, lblStyle);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics
			 * .g2d.Batch, float)
			 */
			@Override
			public void draw(Batch batch, float parentAlpha) {
				super.draw(batch, parentAlpha);
				background.setColor(Assets.CLR_YELLOW_SAND);
				background.draw(batch, getX(), getY(), getWidth(), getHeight());
				if (progress > 0) {
					foreground.setColor(getColor());
					foreground.draw(batch, getX(), getY(), getWidth()
							* progress, getHeight());
				}
				level.setColor(getColor());
				level.setX(getX() + getWidth() / 2f - level.getPrefWidth() / 2f);
				level.setY(getY() + getHeight() / 2f - level.getPrefHeight()
						/ 2f);
				level.setFontScale(getHeight() / 45f);
				level.draw(batch, parentAlpha * alpha);
			}
		}

		private class IconModulator extends ClickListener {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com
			 * .badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				iconAlpha = 1.0f;
				bar.alpha = 1.0f;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.badlogic.gdx.scenes.scene2d.utils.ClickListener#enter(com
			 * .badlogic.gdx.scenes.scene2d.InputEvent, float, float, int,
			 * com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				iconAlpha = 1.0f;
				bar.alpha = 1.0f;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.badlogic.gdx.scenes.scene2d.utils.ClickListener#touchUp(com
			 * .badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				iconAlpha = 1.0f;
				bar.alpha = 1.0f;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.badlogic.gdx.scenes.scene2d.utils.ClickListener#exit(com.
			 * badlogic.gdx.scenes.scene2d.InputEvent, float, float, int,
			 * com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				iconAlpha = 0.5f;
				bar.alpha = 0.5f;
			}
		}
	}

	private class ClickNotifier extends ClickListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic
		 * .gdx.scenes.scene2d.InputEvent, float, float)
		 */
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);

			Color clrFore = new Color(0.6f, 1.0f, 0.1f, 1.0f);
			Color clrBack = new Color(0.3f, 1.0f, 0.1f, 1.0f);

			for (ProfessionElement e : elements.values()) {
				e.setColor(Color.WHITE);
				e.getLabel().setColor(Color.WHITE);
				e.getIcon().setColor(Color.WHITE);
			}

			if (event.getTarget() instanceof ProfessionElement) {
				ProfessionElement e = (ProfessionElement) event.getTarget();
				if (!e.getProfession().isEnabled()) {
					return;
				}
				SoundUtils.play(Assets.SND_CONFIRM, 1.0f, 1.5f);

				for (ProfessionSelectListener l : listeners) {
					l.onSelect(e.getProfession());
				}

				e.setColor(clrBack);
				e.getIcon().setColor(clrBack);
				e.getLabel().setColor(clrFore);
			} else if (event.getTarget() instanceof Label) {

				Label l = (Label) event.getTarget();

				if (l.getParent() instanceof ProfessionElement) {
					ProfessionElement e = (ProfessionElement) l.getParent();
					if (!e.getProfession().isEnabled()) {
						return;
					}
					for (ProfessionSelectListener listener : listeners) {
						listener.onSelect(e.getProfession());
					}

					e.getIcon().setColor(clrBack);
					e.setColor(clrBack);
				}

				l.setColor(clrFore);

			}
		}
	}

	public static interface ProfessionSelectListener {

		void onSelect(Profession profession);
	}
}
