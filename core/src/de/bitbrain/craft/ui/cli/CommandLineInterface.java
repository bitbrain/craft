package de.bitbrain.craft.ui.cli;

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

@StateScoped
public class CommandLineInterface extends Table {

	private TextField textField;
	
	@Inject
	private API api;
	
	@Inject
	private EventBus eventBus;
	
	private boolean initialized;
	
	@PostConstruct
	public void initView() {
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
}
