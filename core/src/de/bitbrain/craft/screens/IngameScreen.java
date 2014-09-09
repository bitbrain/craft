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

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.inject.Inject;

import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.controls.IngameControls;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.core.IconManager;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.EventMessage.MessageType;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;
import de.bitbrain.craft.models.Profession;
import de.bitbrain.craft.tweens.FadeableTween;
import de.bitbrain.craft.ui.ElementInfoConnector;
import de.bitbrain.craft.ui.TabPanel;

/**Os
 * Displays the main game
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class IngameScreen extends AbstractScreen {
	
	private IconManager iconManager = IconManager.getInstance();
	
	private TabPanel tabPanel;
	
	@Inject
	private EventBus eventBus;
	
	private ElementInfoConnector itemConnector;

	public IngameScreen(Profession profession, CraftGame game) {
		super(game);
		SharedInjector.get().injectMembers(this);
	}

	@Override
	protected void onCreateStage(Stage stage) {
		tabPanel = new TabPanel(tweenManager);
		stage.addActor(tabPanel);
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.MenuScreen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);	

		tabPanel.setWidth(Gdx.graphics.getWidth() / 2.3f);
		tabPanel.setHeight(Gdx.graphics.getHeight() / 1.2f);

		tabPanel.padLeft(Gdx.graphics.getWidth() / 10f);
		tabPanel.padBottom(Gdx.graphics.getHeight() / 7f);
		
		tabPanel.addTab("tab1", "ico_jewel_diamond_medium.png", generateItemView());
		tabPanel.addTab("tab2", "ico_jewel_diamond_medium.png", new Label("Tab2", Styles.LBL_BROWN));
		tabPanel.addTab("tab3", "ico_jewel_diamond_medium.png", new Label("Tab3", Styles.LBL_BROWN));
		tabPanel.addTab("tab4", "ico_jewel_diamond_medium.png", new Label("Tab4", Styles.LBL_BROWN));
		
		tabPanel.setTab("tab1");
	}

	@Override
	protected Stage createStage(int width, int height, Batch batch) {
		return new IngameControls(this, new ScreenViewport(), batch);
	}

	@Override
	protected void onDraw(Batch batch, float delta) {
		
	}
	
	/* (non-Javadoc)
	 * @see de.bitbrain.craft.screens.AbstractScreen#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		iconManager.dispose();
		itemConnector.dispose();
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
		itemView.align(Align.left);
		
		// Add data connector
		itemConnector = new ElementInfoConnector(itemView, Item.class);
		eventBus.subscribe(itemConnector);
		
		// API call to get all items
		Map<Item, Integer> itemMap = API.getOwnedItems(Player.getCurrent().getId());
		for (Entry<Item, Integer> entry : itemMap.entrySet()) {
			eventBus.fireElementEvent(MessageType.ADD, entry.getKey(), entry.getValue());
		}
		
		return itemView;
	}

}
