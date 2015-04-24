package de.bitbrain.craft.animations;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.animations.Animations.AnimationType;
import de.bitbrain.craft.graphics.shader.BlurShader;

public class BlurShaderTween implements TweenAccessor<BlurShader> {
  
  @Override
  public int getValues(BlurShader target, int tweenType, float[] returnValues) {
    AnimationType type = AnimationType.byIndex(tweenType);
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
  public void setValues(BlurShader target, int tweenType, float[] newValues) {
    AnimationType type = AnimationType.byIndex(tweenType);
    switch (type) {
      case SIZE:
        target.setBlurSize(newValues[0]);
        break;
    }
  }

}
