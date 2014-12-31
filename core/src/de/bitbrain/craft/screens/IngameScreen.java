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

package de.bitbrain.craft.screens;

import java.util.Map;
import java.util.Map.Entry;

import net.engio.mbassy.listener.Handler;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.audio.SoundUtils;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.Icon;
import de.bitbrain.craft.core.IconManager;
import de.bitbrain.craft.core.professions.ProfessionLogicFactory;
import de.bitbrain.craft.events.ElementEvent;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.KeyEvent;
import de.bitbrain.craft.inject.PostConstruct;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.ui.ProfessionView;
import de.bitbrain.craft.ui.Tabs;
import de.bitbrain.craft.ui.elements.ElementConnector;
import de.bitbrain.craft.ui.widgets.RecipeWidget;
import de.bitbrain.craft.ui.widgets.TabWidget;
import de.bitbrain.craft.util.DragDropHandler;

/**
 * Displays the main game
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class IngameScreen extends AbstractScreen {
	
	@Inject
	private DragDropHandler dragDropHandler;
	
	@Inject
	private API api;
	
	@Inject
	private IconManager iconManager;
	
	@Inject
	private TabWidget tabView;
	
	@Inject 
	private RecipeWidget recipeView;
	
	private ElementConnector itemConnector, recipeConnector;
	
	private ProfessionView professionView;
	
	private Profession profession = Profession.ALCHEMIST;
	
	@PostConstruct	
	public void init() {
		professionView = new ProfessionView(ProfessionLogicFactory.create(profession));
	}

	@Override
	protected void onCreateStage(Stage stage) {
		Container<TabWidget> container = new Container<TabWidget>(tabView);
		stage.addActor(container);
		stage.addActor(tabView);
		stage.addActor(professionView);
		
		tabView.addTab(Tabs.PROFILE, Icon.PROFILE, new Label("Profile", Styles.LBL_TEXT));		
		tabView.addTab(Tabs.ITEMS, Icon.ITEMS, generateItemView());
		tabView.addTab(Tabs.CRAFTING, Icon.HAMMER, recipeView);
		tabView.setTab(Tabs.ITEMS);
	}
	
	@Override
	public void resize(int width, int height) {
		final float paddingFactor = 1.1f;
		super.resize(width, height);
		tabView.setWidth(Sizes.worldWidth() / 2f);
		tabView.setHeight(Sizes.worldHeight() / paddingFactor);
		tabView.setX((Sizes.worldHeight() - (Sizes.worldHeight() / paddingFactor)) / 2f);
		tabView.setY((Sizes.worldHeight() - (Sizes.worldHeight() / paddingFactor)) / 2f);
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		iconManager.dispose();
		itemConnector.dispose();
		recipeConnector.dispose();
		dragDropHandler.clear();
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#onUpdate(float)
	 */
	@Override
	protected void onUpdate(float delta) {
		iconManager.update();
	}

	@Override
	protected void onShow() {
	}
	
	private Actor generateItemView() {		
		VerticalGroup itemView = new VerticalGroup();
		itemView.align(Align.left).fill().pad(Sizes.borderPadding());
		itemConnector = new ElementConnector(itemView, Item.class);		
		Map<Item, Integer> itemMap = api.getOwnedItems(Player.getCurrent().getId());
		for (Entry<Item, Integer> entry : itemMap.entrySet()) {
			eventBus.fireEvent(new ElementEvent<Item>(EventType.ADD, entry.getKey(), entry.getValue()));
		}		
		return generateScrollPane(itemView);
	}
	
	private ScrollPane generateScrollPane(Actor actor) {
		ScrollPane pane = new ScrollPane(actor);
		pane.setCancelTouchFocus(false);
		pane.setScrollingDisabled(true, false);
		return pane;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#onDraw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	protected void onDraw(Batch batch, float delta) { }
	
	@Override
	protected Viewport createViewport() {
		return new FillViewport(Sizes.worldWidth(), Sizes.worldHeight());
	}
	
	@Handler
	void onEvent(KeyEvent event) {
		if (event.getKey() == Keys.ESCAPE || event.getKey() == Keys.BACK) {
			setScreen(ProfessionScreen.class);
			SoundUtils.play(Assets.SND_ABORT, 1.0f, 0.7f);
		}
	}
}