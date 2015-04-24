package de.bitbrain.craft.graphics.shader;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Handles multiple shaders for a single target
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.1
 * @version 0.1
 */
public interface ShaderManager {

  /**
	 * 
	 */
  void add(ShadeArea shaderTarget, Shader<?>... shaders);

  /**
	 * 
	 * 
	 */
  void clear();

  /**
   * 
   * 
   * @return
   */
  int size();

  /**
   * 
   * 
   * @return
   */
  boolean isEmpty();

  void dispose();

  void resize(int width, int height);

  void updateAndRender(Batch batch, float delta);

}