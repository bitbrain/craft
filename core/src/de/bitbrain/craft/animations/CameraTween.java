package de.bitbrain.craft.animations;

import com.badlogic.gdx.graphics.Camera;

import de.bitbrain.craft.animations.TweenAnimations.TweenType;

public class CameraTween extends AbstractTween<Camera> {

  @Override
  protected int getValues(Camera target, TweenType type, float[] returnValues) {
    switch (type) {
      case POS_X:
        returnValues[0] = target.position.x;
        return 1;
      case POS_Y:
        returnValues[0] = target.position.y;
        return 1;
      default:
        return 0;
    }
  }

  @SuppressWarnings("incomplete-switch")
  @Override
  protected void setValues(Camera target, TweenType type, float[] newValues) {
    switch (type) {
      case POS_X:
        target.position.x = newValues[0];
        break;
      case POS_Y:
        target.position.y = newValues[0];
        break;
    }
  }

}
