package de.bitbrain.craft.graphics.shader;


import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Handles multiple shaders for a single target
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.1
 * @version 0.1
 */
public interface ShadeArea {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * 
	 * 
	 * @param batch
	 * @param delta
	 * @param currentShader
	 */
	void draw(Batch batch, float delta);
}