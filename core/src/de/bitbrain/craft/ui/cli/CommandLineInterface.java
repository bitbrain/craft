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

package de.bitbrain.craft.ui.cli;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.inject.StateScoped;
import de.bitbrain.craft.ui.cli.commands.AddCommand;

/**
 * UI implementation of a CLI
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class CommandLineInterface extends Table {
	
	private static final int BUFFER_SIZE = 50;

	private TextField textField;
	
	@Inject
	private CommandHandler commandHandler;
	
	@Inject
	private API api;
	
	@Inject
	private EventBus eventBus;
	
	private boolean initialized;
	
	private History history;
	
	@PostConstruct
	public void initView() {
		history = new History(BUFFER_SIZE);
		commandHandler.register("add", new AddCommand());
		setVisible(false);
		this.setZIndex(1000);
		align(Align.left);
		setWidth(Sizes.worldWidth() -1);
		eventBus.subscribe(this);
	}
	
	@Override
	public void act(float delta) {
		if (!initialized) {
			initialize();
			initialized = true;
		}
		if (Gdx.input.isKeyJustPressed(Keys.F3)) {
			setVisible(!isVisible());
			if (isVisible()) {
				getStage().setKeyboardFocus(textField);
			}
		}
		if (isVisible()) {
			if (!textField.getText().isEmpty() && Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				commandHandler.executeString(api, textField.getText());
				history.add(textField.getText());
				textField.setText("");
				textField.setCursorPosition(0);
			}
			if (!history.isEmpty() && Gdx.input.isKeyJustPressed(Keys.UP)) {
				textField.setText(history.back());
				textField.setCursorPosition(textField.getText().length());
			}
			if (!history.isEmpty() && Gdx.input.isKeyJustPressed(Keys.DOWN)) {
				textField.setText(history.forward());
				textField.setCursorPosition(textField.getText().length());
			}
		}
		super.act(delta);
	}
	
	private void initialize() {
		setBackground(new NinePatchDrawable(Styles.ninePatch(Assets.TEX_PANEL_TRANSPARENT_9patch, Sizes.panelTransparentRadius())));
		textField = new TextField("", Styles.TXT_COMMANDLINE);
		textField.setWidth(getWidth());
		LabelStyle consoleStyle = new LabelStyle();
		consoleStyle.font = SharedAssetManager.get(Assets.FNT_MONO, BitmapFont.class);
		consoleStyle.fontColor = Color.GRAY;
		add(new Label("$ ", consoleStyle));
		add(textField).width(getWidth());	
		setY(Sizes.worldHeight() - textField.getHeight());
		setHeight(textField.getHeight());
	}
	
	private class History {
		
		private int bufferSize;
		
		private Stack<String> back, forward;
		
		private String last;
		
		public History(int bufferSize) {
			last = "";
			back = new Stack<String>();
			forward = new Stack<String>();
			this.bufferSize = bufferSize;
		}
		
		public boolean isEmpty() {
			return back.isEmpty() && forward.isEmpty();
		}
		
		public int size() {
			return back.size() + forward.size();
		}
		
		public void add(String line) {
			if (size() >= bufferSize) {
				if (!back.isEmpty()) {
					back.remove(0);
				} else if (!forward.isEmpty()){
					forward.remove(0);
				}
			}
			for (int i = 0; i < forward.size(); ++i) {
				back.add(forward.pop());
			}
			back.add(line);
			last = line;
		}
		
		public String back() {
			if (!back.isEmpty()) {
				last = back.pop();
				forward.add(last);
			}
			return last;
		}
		
		public String forward() {
			if (!forward.isEmpty()) {
				last = forward.pop();
				back.add(last);
				return last;
			}
			return "";
		}
	}
}
