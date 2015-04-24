package de.bitbrain.craft.animations;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.animations.Animations.AnimationType;
import de.bitbrain.craft.util.FloatValueProvider;

public class FloatValueTween implements TweenAccessor<FloatValueProvider> {

  @Override
  public int getValues(FloatValueProvider target, int tweenType, float[] returnValues) {
    AnimationType type = AnimationType.byIndex(tweenType);
    switch (type) {
      case VALUE:
        returnValues[0] = target.getValue();
        return 1;
      default:
        return 0;
    }
  }

  @SuppressWarnings("incomplete-switch")
  @Override
  public void setValues(FloatValueProvider target, int tweenType, float[] newValues) {
    AnimationType type = AnimationType.byIndex(tweenType);
    switch (type) {
      case VALUE:
        target.setValue(newValues[0]);
        break;
    }
  }

}