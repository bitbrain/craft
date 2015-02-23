package de.bitbrain.craft.graphics.shader;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Simple shader (wrapper) for {@see ShaderProgram}
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface Shader<Type extends Shader<Type>> {

	/**
	 * 
	 * @return
	 */
	ShaderProgram getProgram();

	/**
	 * 
	 * @param delta
	 */
	void update(float delta);

	/**
	 * 
	 * @param behavior
	 */
	void setBehavior(ShaderBehavior<Type> behavior);
}