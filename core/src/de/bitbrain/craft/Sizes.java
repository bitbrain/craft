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

package de.bitbrain.craft;

import com.badlogic.gdx.Gdx;

/**
 * Contains all sizes
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public final class Sizes {

	public static int worldWidth() {
		return 1020;
	}

	public static int worldHeight() {
		return 600;
	}

	public static float borderPadding() {
		return 20f;
	}

	public static float tabPadding() {
		return worldHeight() / 25f;
	}

	public static int panelRadius() {
		return 15;
	}

	public static int panelTransparentRadius() {
		return 8;
	}

	public static int dragIconSize() {
		return 100;
	}

	public static float worldMouseX() {
		return Gdx.input.getX();
	}

	public static float worldMouseY() {
		return Gdx.input.getY();
	}

	public static float worldScreenFactorX() {
		return (float) Gdx.graphics.getWidth() / (float) worldWidth();
	}

	public static float worldScreenFactorY() {
		return (float) Gdx.graphics.getHeight() / (float) worldHeight();
	}
}