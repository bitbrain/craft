package de.bitbrain.craft.graphics.shader;

/**
 * Simple behavior to modify shaders
 *
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 0.1
 * @version 0.1
 */
public interface ShaderBehavior<Type extends Shader<Type>> {

  // ===========================================================
  // Constants
  // ===========================================================

  // ===========================================================
  // Methods
  // ===========================================================

  void update(float delta, Type shader);

}