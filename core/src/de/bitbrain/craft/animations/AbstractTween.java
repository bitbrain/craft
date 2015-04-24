package de.bitbrain.craft.animations;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.animations.Animations.AnimationType;

abstract class AbstractTween<T>  implements TweenAccessor<T> {

  @Override
  public final int getValues(T target, int tweenType, float[] returnValues) {
    AnimationType type = AnimationType.byIndex(tweenType);
    return getValues(target, type, returnValues);
  }

  @Override
  public final void setValues(T target, int tweenType, float[] newValues) {
    AnimationType type = AnimationType.byIndex(tweenType); 
    setValues(target, type, newValues);
  }

  protected abstract int getValues(T target, AnimationType type, float[] returnValues);  
  protected abstract void setValues(T target, AnimationType type, float[] newValues);
}
