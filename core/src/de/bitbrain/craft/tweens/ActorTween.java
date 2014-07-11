package de.bitbrain.craft.tweens;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorTween implements TweenAccessor<Actor> {

	public static final int ALPHA = 1;

	public static final int POPUP = 2;

	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case POPUP:
			returnValues[0] = target.getY();
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {

		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g,
					target.getColor().b, newValues[0]);
			break;
		case POPUP:
			target.setPosition(target.getX(), newValues[0]);
			break;
		}
	}

}