package de.bitbrain.craft.tweens;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.math.Vector2;

public class VectorTween implements TweenAccessor<Vector2> {

	public static final int X = 1;

	public static final int Y = 2;

	@Override
	public int getValues(Vector2 target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case X:
			returnValues[0] = target.x;
			return 1;
		case Y:
			returnValues[0] = target.y;
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void setValues(Vector2 target, int tweenType, float[] newValues) {

		switch (tweenType) {
		case X:
			target.x = newValues[0];
			break;
		case Y:
			target.y = newValues[0];
			break;
		}
	}

}