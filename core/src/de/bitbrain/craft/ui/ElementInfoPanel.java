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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.google.inject.Inject;

import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.IconManager.Icon;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.Event.MessageType;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item.Rarity;

/**
 * List element which shows basic element info
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ElementInfoPanel extends HorizontalGroup {
	
	private Label amountLabel;
	
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
		name.setX(10f);
		amountLabel = new Label(data.getAmount() + " ", Styles.LBL_ITEM);
		amountLabel.setColor(Color.YELLOW);
		RarityIcon icon = new RarityIcon(data.getIcon());
		
		icon.setWidth(name.getHeight() * 2);
		icon.setHeight(name.getHeight() * 2);
		addActor(amountLabel);
		addActor(icon);
		addActor(name);
		
		registerEvents();
	}
	
	public void setData(ElementData data) {
		this.data = data;				
		name.setText(" " + data.getName());
		name.setColor(data.getRarity().getColor());
		setAmount(data.getAmount());
		icon.setSource(data.getIcon());
	}
	
	public ElementData getData() {
		return data;
	}
	
	public void setAmount(int amount) {
		amountLabel.setText(amount + " ");
	}
	
	public static interface ElementData {
		
		String getId();
		
		Icon getIcon();
		
		String getDescription();
		
		Rarity getRarity();
		
		String getName();
		
		int getAmount();
	}
	
	private void registerEvents() {
		final ElementInfoPanel p = this;	
		addListener(new DragListener() {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.DragListener#dragStart(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int)
			 */
			@Override
			public void dragStart(InputEvent event, float x, float y,
					int pointer) {
				eventBus.fireMouseEvent(MessageType.MOUSEDRAG, p, x, y);
			}
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.DragListener#dragStop(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int)
			 */
			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				eventBus.fireMouseEvent(MessageType.MOUSEDROP, p, x, y);
			}
		});
	}
}
