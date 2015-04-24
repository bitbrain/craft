package de.bitbrain.craft.animations;

import de.bitbrain.craft.animations.Animations.AnimationType;
import de.bitbrain.craft.util.FloatValueProvider;

public class FloatValueTween extends AbstractTween<FloatValueProvider> {

  @Override
  public int getValues(FloatValueProvider target, AnimationType type, float[] returnValues) {
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
  public void setValues(FloatValueProvider target, AnimationType type, float[] newValues) {
    switch (type) {
      case VALUE:
        target.setValue(newValues[0]);
        break;
    }
  }

}