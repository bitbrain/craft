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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.SharedAssetManager;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.SharedInjector;

/**
 * List element which shows basic element info
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ElementInfoPanel extends HorizontalGroup {
	
	private Label name;
	
	private RarityIcon icon;
	
	private ElementData data;
	
	@Inject
	EventBus eventBus;
	
	public ElementInfoPanel(ElementData data) {		
		SharedInjector.get().injectMembers(this);
		this.data = data;
		Label name = new Label(" " + data.getName(), Styles.LBL_ITEM);
		name.setColor(data.getRarity().getColor());
		name.setX(30f);
		RarityIcon icon = new RarityIcon(data);		
		icon.setWidth(name.getHeight() * 4);
		icon.setHeight(name.getHeight() * 4);
		pad(10f);
		addActor(icon);
		addActor(name);
		fill();
		registerEvents();
	}
	
	public void setData(ElementData data) {
		this.data = data;				
		name.setText(" " + data.getName());
		name.setColor(data.getRarity().getColor());
		setAmount(data.getAmount());
		icon.setSource(data);
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Texture texture = SharedAssetManager.get(Assets.TEX_PANEL_ITEM, Texture.class);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());		
		super.draw(batch, parentAlpha);
	}
	
	public ElementData getData() {
		return data;
	}
	
	public void setAmount(int amount) {
		data.setAmount(amount);
	}
	
	private void registerEvents() {
		addListener(new DragListener() {
			@Override
			public void dragStart(InputEvent event, float x, float y, int pointer) {
				eventBus.fireEvent(new MouseEvent<ElementData>(EventType.MOUSEDRAG, getData(), x, y));
			}
			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				eventBus.fireEvent(new MouseEvent<ElementData>(EventType.MOUSEDROP, getData(), x, y));
			}
		});
	}
}