package de.bitbrain.craft.animations;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.animations.Animations.AnimationType;
import de.bitbrain.craft.util.Fadeable;

public class FadeableTween implements TweenAccessor<Fadeable> {

  @Override
  public int getValues(Fadeable target, int tweenType, float[] returnValues) {
    AnimationType type = AnimationType.byIndex(tweenType);
    switch (type) {
      case ALPHA:
        returnValues[0] = target.getAlpha();
        return 1;
      default:
        return 0;
    }
  }

  @SuppressWarnings("incomplete-switch")
  @Override
  public void setValues(Fadeable target, int tweenType, float[] newValues) {
    AnimationType type = AnimationType.byIndex(tweenType);
    switch (type) {
      case ALPHA:
        target.setAlpha(newValues[0]);
        break;
    }
  }

}