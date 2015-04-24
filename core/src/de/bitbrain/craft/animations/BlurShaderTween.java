package de.bitbrain.craft.animations;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.graphics.shader.BlurShader;

public class BlurShaderTween implements TweenAccessor<BlurShader> {

  public static final int SIZE = 1;

  @Override
  public int getValues(BlurShader target, int tweenType, float[] returnValues) {
    switch (tweenType) {
      case SIZE:
        returnValues[0] = target.getBlurSize();
        return 1;
      default:
        return 0;
    }
  }

  @Override
  public void setValues(BlurShader target, int tweenType, float[] newValues) {
    switch (tweenType) {
      case SIZE:
        target.setBlurSize(newValues[0]);
        break;
    }
  }

}
