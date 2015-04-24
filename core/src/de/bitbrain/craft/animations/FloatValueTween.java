package de.bitbrain.craft.animations;

import de.bitbrain.craft.animations.TweenAnimations.TweenType;
import de.bitbrain.craft.util.FloatValueProvider;

public class FloatValueTween extends AbstractTween<FloatValueProvider> {

  @Override
  public int getValues(FloatValueProvider target, TweenType type, float[] returnValues) {
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
  public void setValues(FloatValueProvider target, TweenType type, float[] newValues) {
    switch (type) {
      case VALUE:
        target.setValue(newValues[0]);
        break;
    }
  }

}