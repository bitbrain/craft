package de.bitbrain.craft.animations;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.animations.TweenAnimations.TweenType;

abstract class AbstractTween<T>  implements TweenAccessor<T> {

  @Override
  public final int getValues(T target, int tweenType, float[] returnValues) {
    TweenType type = TweenType.byIndex(tweenType);
    return getValues(target, type, returnValues);
  }

  @Override
  public final void setValues(T target, int tweenType, float[] newValues) {
    TweenType type = TweenType.byIndex(tweenType); 
    setValues(target, type, newValues);
  }

  protected abstract int getValues(T target, TweenType type, float[] returnValues);  
  protected abstract void setValues(T target, TweenType type, float[] newValues);
}
