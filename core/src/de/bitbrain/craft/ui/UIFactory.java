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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.audio.SoundUtils;

/**
 * Factory which creates UI components
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class UIFactory {
	
	/**
	 * Creates a green primary button
	 * 
	 * @param caption text of the button
	 * @return new button instance
	 */
	public static TextButton createPrimaryButton(String caption) {
		final TextButton textButton = new TextButton(caption, Styles.BTN_GREEN);
		textButton.addCaptureListener(new ClickListener() {
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchDown(event, x, y, pointer, button);
				textButton.padTop(5f);
				textButton.invalidate();
				return true;
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				textButton.padTop(0f);
				textButton.invalidate();
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#exit(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void exit(InputEvent event, float x, float y, int pointer,
					Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				textButton.padTop(0f);
				textButton.invalidate();
			}
			
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#enter(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, com.badlogic.gdx.scenes.scene2d.Actor)
			 */
			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);				
				if (textButton.isPressed()) {
					textButton.padTop(5f);
					textButton.invalidate();
				}
			}
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				SoundUtils.play(Assets.SND_BEEP, 1.0f, 1.2f);
			}
		});
		return textButton;
	}
	
	public static TextButton createAbortButton(String text) {
		TextButton button = createPrimaryButton(text);
		button.setStyle(Styles.BTN_RED);
		return button;
	}

}
