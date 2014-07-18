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
import java.util.Map.Entry;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.audio.ButtonSoundListener;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.tweens.ActorTween;

/**
 * This element shows a selection for all professions. It is also possible to add a listener
 * which detects, if a certain profession has been clicked (as an enumeration)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ProfessionSelection extends Table implements EventListener {
	
	private Map<Cell<?>, ProfessionElement> elements = new HashMap<Cell<?>, ProfessionElement>();
	
	public ProfessionSelection(TweenManager tweenManager) {
		
		this.columnDefaults(Profession.values().length);
		
		for (int index = 0; index < Profession.values().length; index++) {			
			
			Profession f = Profession.values()[index];
			
			ProfessionElement element = new ProfessionElement(f.getName(), Styles.PROFESSION_BUTTON, f);
			element.addCaptureListener(this);
			element.addCaptureListener(new ButtonSoundListener(1.3f));

			Cell<?> cell = add(element);
			elements.put(cell, element);
			
			animateElement(index, element, tweenManager);
		}
		
		this.pad(Gdx.graphics.getWidth() / 40f);
		pack();
	}	
	

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.EventListener#handle(com.badlogic.gdx.scenes.scene2d.Event)
	 */
	@Override
	public boolean handle(Event event) {
		
		if (event.isCapture()) {
			notify(event, true);
			return true;
		}
		
		return false;
	}
	
	
	/* (non-Javadoc)
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
		cell.width((Gdx.graphics.getWidth() / 1.2f) / Profession.values().length)
		.height(Gdx.graphics.getHeight() / 1.2f)
		.pad(Gdx.graphics.getWidth() / 70f);
		element.getLabel().setFontScale(element.getWidth() / 280f);
		element.padTop(element.getHeight() / 2f);
	}
	
	private void animateElement(int index, ProfessionElement element, TweenManager tweenManager) {
		element.getColor().a = 0f;
		Tween.to(element, ActorTween.ALPHA, 1.0f)
		.delay(index / 4f)
		.target(1.0f)
		.ease(TweenEquations.easeInOutQuad)
		.start(tweenManager);
	}
	
	public class ProfessionElement extends TextButton {
		
		private Sprite icon;
		
		private float iconAlpha = 0.5f;

		/**
		 * @param text
		 * @param skin
		 */
		public ProfessionElement(String text, TextButtonStyle style, Profession profession) {
			super(text, style);
			
			Texture tex = getProfessionTexture(profession);
			
			if (tex != null) {
				icon = new Sprite(tex);
			}
			
			addCaptureListener(new IconModulator());
		}
		
		/* (non-Javadoc)
		 * @see com.badlogic.gdx.scenes.scene2d.ui.TextButton#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
		 */
		@Override
		public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);
			
			if (icon != null) {
				icon.setSize(getWidth() / 1.5f, getWidth() / 1.5f);
				icon.setPosition(getX() + getWidth() / 2 - icon.getWidth() / 2, getY() + getHeight() / 2.5f);
				icon.draw(batch, parentAlpha * iconAlpha * getColor().a);
			}
			
		}
		
		private Texture getProfessionTexture(Profession profession) {			
			if (profession.getIcon() != null) {
				return SharedAssetManager.get(profession.getIcon(), Texture.class);
			} else {
				return null;
			}
		}
		
		
		private class IconModulator extends ClickListener {
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				iconAlpha = 1.0f;
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#enter(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				iconAlpha = 1.0f;
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				iconAlpha = 1.0f;
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#exit(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				iconAlpha = 0.5f;
			}
		}
		
	}
}
