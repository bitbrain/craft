package de.bitbrain.craft.animations;

import de.bitbrain.craft.animations.TweenAnimations.TweenType;
import de.bitbrain.craft.util.Fadeable;

public class FadeableTween extends AbstractTween<Fadeable> {

  @Override
  public int getValues(Fadeable target, TweenType type, float[] returnValues) {
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
  public void setValues(Fadeable target, TweenType type, float[] newValues) {
    switch (type) {
      case ALPHA:
        target.setAlpha(newValues[0]);
        break;
    }
  }

}