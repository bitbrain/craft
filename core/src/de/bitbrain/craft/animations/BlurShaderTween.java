package de.bitbrain.craft.animations;

import de.bitbrain.craft.animations.Animations.AnimationType;
import de.bitbrain.craft.graphics.shader.BlurShader;

public class BlurShaderTween extends AbstractTween<BlurShader> {
  
  @Override
  public int getValues(BlurShader target, AnimationType type, float[] returnValues) {
    switch (type) {
      case SIZE:
        returnValues[0] = target.getBlurSize();
        return 1;
      default:
        return 0;
    }
  }

  @SuppressWarnings("incomplete-switch")
  @Override
  public void setValues(BlurShader target, AnimationType type, float[] newValues) {
    switch (type) {
      case SIZE:
        target.setBlurSize(newValues[0]);
        break;
    }
  }

}
