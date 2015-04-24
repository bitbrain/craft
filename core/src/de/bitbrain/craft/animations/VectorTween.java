package de.bitbrain.craft.animations;

import com.badlogic.gdx.math.Vector2;

import de.bitbrain.craft.animations.Animations.AnimationType;

public class VectorTween extends AbstractTween<Vector2> {

  @Override
  public int getValues(Vector2 target, AnimationType type, float[] returnValues) {
    switch (type) {
      case POS_X:
        returnValues[0] = target.x;
        return 1;
      case POS_Y:
        returnValues[0] = target.y;
        return 1;
      default:
        return 0;
    }
  }

  @SuppressWarnings("incomplete-switch")
  @Override
  public void setValues(Vector2 target, AnimationType type, float[] newValues) {
    switch (type) {
      case POS_X:
        target.x = newValues[0];
        break;
      case POS_Y:
        target.y = newValues[0];
        break;
    }
  }

}