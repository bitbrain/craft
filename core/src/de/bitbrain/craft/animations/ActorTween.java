package de.bitbrain.craft.animations;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.Actor;

import de.bitbrain.craft.animations.Animations.AnimationType;

public class ActorTween implements TweenAccessor<Actor> {

  @Override
  public int getValues(Actor target, int tweenType, float[] returnValues) {
    AnimationType type = AnimationType.byIndex(tweenType);
    switch (type) {
      case ALPHA:
        returnValues[0] = target.getColor().a;
        return 1;
      case POS_X:
        returnValues[0] = target.getX();
        return 1;
      case POS_Y:
        returnValues[0] = target.getY();
        return 1;
      case SCALE:
        returnValues[0] = target.getScaleX();
        returnValues[1] = target.getScaleY();
        return 1;
      case SCALE_X:
        returnValues[0] = target.getScaleX();
        return 1;        
      case SCALE_Y:
        returnValues[0] = target.getScaleY();
        return 1;
      default:
        return 0;
    }
  }

  @Override
  public void setValues(Actor target, int tweenType, float[] newValues) {
    AnimationType type = AnimationType.byIndex(tweenType);
    switch (type) {
      case ALPHA:
        target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
        break;
      case POS_X:
        target.setX(newValues[0]);
        break;
      case POS_Y:
        target.setPosition(target.getX(), newValues[0]);
        break;
      case SCALE:
        target.setScaleX(newValues[0]);
        target.setScaleY(newValues[1]);
        break;
      case SCALE_X:
        target.setScaleX(newValues[0]);
        break;
      case SCALE_Y:
        target.setScaleY(newValues[0]);
        break;
      default:
        break;
    }
  }

}