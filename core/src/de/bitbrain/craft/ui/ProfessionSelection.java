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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import de.bitbrain.craft.Styles;
import de.bitbrain.craft.audio.ButtonSoundListener;
import de.bitbrain.craft.models.Profession;

/**
 * This element shows a selection for all professions. It is also possible to add a listener
 * which detects, if a certain profession has been clicked (as an enumeration)
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ProfessionSelection extends Table implements EventListener {
	
	public ProfessionSelection() {
		
		this.columnDefaults(Profession.values().length);
		
		for (Profession f : Profession.values()) {			
			ProfessionElement element = new ProfessionElement(f.getName(), Styles.PROFESSION_BUTTON, f);
			element.addCaptureListener(this);
			element.addCaptureListener(new ButtonSoundListener(1.3f));
			element.setBounds(0, 0, 100, 100);

			add(element)
			.width((Gdx.graphics.getWidth() / 1.2f) / Profession.values().length)
			.height(Gdx.graphics.getHeight() / 1.2f)
			.pad(Gdx.graphics.getWidth() / 70f);
			
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
	
	public class ProfessionElement extends TextButton {
		
		private Profession profession;

		/**
		 * @param text
		 * @param skin
		 */
		public ProfessionElement(String text, TextButtonStyle style, Profession profession) {
			super(text, style);
			this.profession = profession;
		}
		
	}
}
