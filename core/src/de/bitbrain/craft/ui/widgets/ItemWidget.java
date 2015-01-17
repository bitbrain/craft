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

package de.bitbrain.craft.ui.widgets;

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
import de.bitbrain.craft.Bundles;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.API;
import de.bitbrain.craft.events.Event.EventType;
import de.bitbrain.craft.events.EventBus;
import de.bitbrain.craft.events.MouseEvent;
import de.bitbrain.craft.graphics.GraphicsFactory;
import de.bitbrain.craft.inject.SharedInjector;
import de.bitbrain.craft.models.Item;
import de.bitbrain.craft.models.Player;

/**
 * List element which shows basic element info
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class ItemWidget extends HorizontalGroup {

	private final String GAP = "            ";

	private Label name;

	private IconWidget icon;

	private NinePatch background;

	private Item item;

	private int amount;

	@Inject
	private EventBus eventBus;

	@Inject
	private API api;

	private boolean craftable;

	public ItemWidget(Item item, int amount) {
		try {
			background = GraphicsFactory.createNinePatch(
					Assets.TEX_PANEL_TRANSPARENT_9patch,
					Sizes.panelTransparentRadius());
			SharedInjector.get().injectMembers(this);
			this.item = item;
			this.amount = amount;
			craftable = isElementCraftable();
			this.name = new Label(Bundles.items.get(item.getId().toString()) + GAP, Styles.LBL_ITEM);
			icon = new IconWidget(item.getIcon(), amount);
			icon.setWidth(name.getHeight() * 4);
			icon.setHeight(name.getHeight() * 4);
			addActor(icon);
			Actor right = generateRight(item);
			addActor(right);
			pad(10f);
			registerEvents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setItem(Item item, int amount) {
		this.item = item;
		name.setText(item.getId() + GAP);
		name.setColor(item.getRarity().getColor());
		setAmount(item, amount);
		craftable = isElementCraftable();
	}

	public int getAmount() {
		return amount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup#draw(com.badlogic.gdx.
	 * graphics.g2d.Batch, float)
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		setWidth(getParent().getWidth() - Sizes.borderPadding() * 2);
		background.getColor().a = parentAlpha;
		background.draw(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}

	public void setAmount(Item item, int amount) {
		icon.setSource(item.getIcon(), amount);
	}

	private boolean isElementCraftable() {
		return api.canCraft(Player.getCurrent(), item.getId());
	}

	private Actor generateRight(Item item) {
		VerticalGroup layout = new VerticalGroup();
		layout.align(Align.left);
		layout.padLeft(15f);
		layout.padTop(15f);
		name.setColor(item.getRarity().getColor());
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
			public void dragStart(InputEvent event, float x, float y,
					int pointer) {
				if (amount > 0) {
					eventBus.fireEvent(new MouseEvent<Item>(
							EventType.MOUSEDRAG, item, x, y));
				}
			}

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				if (amount > 0) {
					eventBus.fireEvent(new MouseEvent<Item>(
							EventType.MOUSEDROP, item, x, y));
				}
			}
		});
		icon.addCaptureListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic
			 * .gdx.scenes.scene2d.InputEvent, float, float, int, int)
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
					eventBus.fireEvent(new MouseEvent<Item>(EventType.CLICK,
							item, x, y));
				}
			}
		});
	}
}