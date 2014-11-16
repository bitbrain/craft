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

package de.bitbrain.craft.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.google.inject.Inject;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Player;

/**
 * List element which shows basic element info
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ElementPanel extends HorizontalGroup {
	
	private Label name;
	
	private ElementIcon icon;
	
	private ElementData data;
	
	private NinePatch background;
	
	@Inject
	private EventBus eventBus;
	
	@Inject
	private API api;
	
	private boolean craftable;
	
	public ElementPanel(ElementData data) {
		try {
			background = Styles.ninePatch(Assets.TEX_PANEL_TRANSPARENT_9patch, Sizes.panelTransparentRadius());
			SharedInjector.get().injectMembers(this);
			this.data = data;
			craftable = isElementCraftable();
			this.name = new Label(data.getName(), Styles.LBL_ITEM);
			icon = new ElementIcon(data);		
			icon.setWidth(name.getHeight() * 4);
			icon.setHeight(name.getHeight() * 4);
			addActor(icon);
			Actor right = generateRight(data);
			addActor(right);
			pad(10f);
			registerEvents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setData(ElementData data) {
		this.data = data;				
		name.setText(data.getName());
		name.setColor(data.getRarity().getColor());
		setAmount(data.getAmount());
		icon.setSource(data);
		craftable = isElementCraftable();
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		setWidth(getParent().getWidth() - Sizes.borderPadding() * 2);
		background.getColor().a = parentAlpha;
		background.draw(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}
	
	public ElementData getData() {
		return data;
	}
	
	public void setAmount(int amount) {
		data.setAmount(amount);
	}
	
	private boolean isElementCraftable() {
		return api.canCraft(Player.getCurrent(), data.getId());
	}
	
	private Actor generateRight(ElementData data) {
		VerticalGroup layout = new VerticalGroup();
		layout.align(Align.left);
		layout.padLeft(15f);
		layout.padTop(15f);
		name.setColor(data.getRarity().getColor());
		name.setFontScale(0.85f);
		
		String textDescription = "Click to craft";
		Color colorDescription = Assets.CLR_INACTIVE;
		if (!craftable) {
			textDescription = "Can not craft";
			colorDescription = Color.RED;
		}		
		Label description = new Label(textDescription, Styles.LBL_TEXT);
		description.setFontScale(0.7f);
		description.setColor(colorDescription);	
		description.getColor().a = 0.5f;
		layout.addActor(name);
		VerticalGroup descContainer = new VerticalGroup();
		descContainer.addActor(description);
		descContainer.padTop(15f);
		layout.addActor(descContainer);
		return layout;
	}
	
	private void registerEvents(Actor actor) {
		// Allow dragging for icons only
		icon.addListener(new DragListener() {
			@Override
			public void dragStart(InputEvent event, float x, float y, int pointer) {
				if (data.getAmount() > 0) {
					eventBus.fireEvent(new MouseEvent<ElementData>(EventType.MOUSEDRAG, getData(), x, y));
				}
			}
			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				if (data.getAmount() > 0) {
					eventBus.fireEvent(new MouseEvent<ElementData>(EventType.MOUSEDROP, getData(), x, y));
				}
			}
		});
		icon.addCaptureListener(new InputListener() {
			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
		});
		actor.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (craftable) {
					eventBus.fireEvent(new MouseEvent<ElementData>(EventType.CLICK, getData(), x, y));
				}
			}
		});
	}
}