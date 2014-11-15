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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import de.bitbrain.craft.Assets;
import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.Styles;
import de.bitbrain.craft.core.Icon;
import de.bitbrain.craft.inject.StateScoped;

/**
 * Responsive tab view which can be extendable
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class TabView extends Table {
	
	public static final int TAB_WIDTH = 100;
	
	private Cell<Container> left;
	
	private Cell<Container>  right;
	
	public TabView() {
		debug();
		generateLeft();
		generateRight();
		bottom();
	}
	
	/**
	 * Switches to an existing tab. Does nothing if tab can not be found.
	 *  
	 * @param tab
	 */
	public void setTab(String tab) {
		
	}
	
	/**
	 * Registers a new tab with the given icon and the target actor
	 * 
	 * @param id tab id
	 * @param icon tab icon to show
	 * @param actor Content actor
	 */
	public void addTab(String id, Icon icon, Actor content) {
		
	}
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		left.width(width - TAB_WIDTH);
		left.getActor().setWidth(width - TAB_WIDTH);
	}
	
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		left.height(height);
		left.getActor().setHeight(height);
		right.height(height);
		right.getActor().setHeight(height);
	}
	
	private void generateLeft() {
		Container c = new Container();
		c.setBackground(new NinePatchDrawable(Styles.ninePatch(Assets.TEX_PANEL_9patch, Sizes.panelRadius())));
		left = add(c);

	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		super.draw(batch, parentAlpha);
	}
	
	private void generateRight() {
		Container c = new Container();
		c.setBackground(new NinePatchDrawable(Styles.ninePatch(Assets.TEX_PANEL_9patch, Sizes.panelRadius())));
		right = add(c);
		right.fillY();
	}
}
