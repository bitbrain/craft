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

package de.bitbrain.craft.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;

import de.bitbrain.craft.graphics.shader.BlurShader;
import de.bitbrain.craft.graphics.shader.ShadeArea;
import de.bitbrain.craft.graphics.shader.ShaderManager;
import de.bitbrain.craft.graphics.shader.SimpleShaderManager;
import de.bitbrain.craft.inject.StateScoped;

/**
 * Handles blurring
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
@StateScoped
public class GaussianBlur {

	private ShaderManager shaderManager;

	private BlurShader vertBlur, horBlur;

	GaussianBlur(ShadeArea childArea) {
		vertBlur = new BlurShader(false);
		vertBlur.setBlurSize(0f);
		horBlur = new BlurShader(true);
		horBlur.setBlurSize(0f);
		shaderManager = new SimpleShaderManager();
		shaderManager.add(childArea, vertBlur, horBlur, vertBlur, horBlur);
	}

	public BlurShader getVerticalBlur() {
		return vertBlur;
	}

	public BlurShader getHorizontalBlur() {
		return horBlur;
	}

	public void resize(int width, int height) {
		shaderManager.resize(width, height);
	}

	public void updateAndRender(Batch batch, float delta) {
		shaderManager.updateAndRender(batch, delta);
	}

}
