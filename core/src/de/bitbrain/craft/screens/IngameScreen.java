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
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.google.inject.Inject;

import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.IconManager;
import de.bitbrain.craft.core.professions.ProfessionLogicFactory;
import de.bitbrain.craft.events.ElementEvent;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.KeyEvent;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.tweens.FadeableTween;
import de.bitbrain.craft.ui.DragDropHandler;
import de.bitbrain.craft.ui.ElementInfoConnector;
import de.bitbrain.craft.ui.ProfessionView;
import de.bitbrain.craft.ui.TabPanel;

/**
 * Displays the main game
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class IngameScreen extends AbstractScreen {
	
	@Inject
	private IconManager iconManager;
	
	private TabPanel tabPanel;
	
	@Inject
	private DragDropHandler dragDropHandler;
	
	@Inject
	private API api;
	
	private ElementInfoConnector itemConnector;
	
	private ProfessionView professionView;
	
	public void init(Profession profession) {
		professionView = new ProfessionView(ProfessionLogicFactory.create(profession));
	}

	@Override
	protected void onCreateStage(Stage stage) {
		tabPanel = new TabPanel(tweenManager);
		stage.addActor(tabPanel);
		stage.addActor(professionView);
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		boolean init = inputProcessor == null;
		super.resize(width, height);
		tabPanel.setWidth(width / 2.3f);
		tabPanel.setHeight(height);
		
		professionView.setWidth(width - tabPanel.getWidth() / 0.82f);
		professionView.setHeight(tabPanel.getHeight());
		professionView.setY(height / 7f);
		professionView.setX(tabPanel.getWidth() + width / 12f);

		if (init) {
			tabPanel.addTab("tab1", "ico_jewel_diamond_medium.png", generateItemView());
			tabPanel.addTab("tab2", "ico_jewel_diamond_medium.png", new Label("Tab2", Styles.LBL_BROWN));
			tabPanel.addTab("tab3", "ico_jewel_diamond_medium.png", new Label("Tab3", Styles.LBL_BROWN));
			tabPanel.addTab("tab4", "ico_jewel_diamond_medium.png", new Label("Tab4", Styles.LBL_BROWN));		
			tabPanel.setTab("tab1");
		}
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#onStageDraw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void onStageDraw(Batch batch, float delta) {
		dragDropHandler.draw(batch, delta);
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		iconManager.dispose();
		itemConnector.dispose();
		dragDropHandler.clear();
		eventBus.unsubscribe(itemConnector);
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
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#onFadeOut(float)
	 */
	@Override
	protected void onFadeOut(float parentInterval) {
		
		Tween.to(iconManager, FadeableTween.DEFAULT, parentInterval)
		.target(0f)
		.ease(TweenEquations.easeInOutCubic)
		.start(tweenManager);
		
		super.onFadeOut(parentInterval);
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#onFadeIn(floast)
	 */
	@Override
	protected void onFadeIn(float parentInterval) {
		iconManager.setAlpha(0.0f);
		Tween.to(iconManager, FadeableTween.DEFAULT, parentInterval)
		.target(1f)
		.ease(TweenEquations.easeInOutCubic)
		.start(tweenManager);
		
		super.onFadeIn(parentInterval);
	}
	
	private Actor generateItemView() {
		
		VerticalGroup itemView = new VerticalGroup();
		itemView.align(Align.left).fill();
		itemView.padLeft(10f);
		// Add data connector
		itemConnector = new ElementInfoConnector(itemView, Item.class);
		
		// API call to get all items
		Map<Item, Integer> itemMap = api.getOwnedItems(Player.getCurrent().getId());
		for (Entry<Item, Integer> entry : itemMap.entrySet()) {
			System.out.println(eventBus + " in IngameScreen");
			eventBus.fireEvent(new ElementEvent<Item>(EventType.ADD, entry.getKey(), entry.getValue()));
		}
		
		return itemView;
	}

	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#onDraw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	protected void onDraw(Batch batch, float delta) { }
	
	@Handler
	void onEvent(KeyEvent event) {
		if (event.getKey() == Keys.ESCAPE) {
			setScreen(SharedInjector.get().getInstance(ProfessionScreen.class));
		}
	}

}
