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

package de.bitbrain.craft.ui.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.inject.Inject;

import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.ui.Overlay;
import de.bitbrain.craft.ui.UIFactory;
import de.bitbrain.craft.util.Pair;

/**
 * Dialog which can be accepted or aborted
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Dialog extends VerticalGroup {

	@Inject
	private Overlay overlay;

	private Group buttonLayout;

	Dialog(Pair<String, ClickListener> submit,
			Pair<String, ClickListener> abort, Actor content) {
		SharedInjector.get().injectMembers(this);
		fill();
		addActor(content);
		if (submit != null || abort != null) {
			buttonLayout = new HorizontalGroup();
			addActor(buttonLayout);
		}
		if (abort != null) {
			initAbort(abort);
		}
		if (submit != null) {
			initSubmit(submit);
		}
	}

	void show() {
		overlay.show(this);
	}

	public void hide() {
		overlay.hide();
	}

	private void initSubmit(Pair<String, ClickListener> submit) {
		TextButton button = UIFactory.createPrimaryButton(submit.getFirst());
		button.addCaptureListener(submit.getSecond());
		buttonLayout.addActor(button);
	}

	private void initAbort(Pair<String, ClickListener> abort) {
		TextButton button = UIFactory.createPrimaryButton(abort.getFirst());
		button.addCaptureListener(abort.getSecond());
		button.addCaptureListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Dialog.this.hide();
			}
		});
		buttonLayout.addActor(button);
	}
}
