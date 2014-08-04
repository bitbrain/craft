package de.bitbrain.craft.tweens;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.util.Fadeable;

public class FadeableTween implements TweenAccessor<Fadeable> {

	public static final int DEFAULT = 1;

	@Override
	public int getValues(Fadeable target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case DEFAULT:
			returnValues[0] = target.getAlpha();
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void setValues(Fadeable target, int tweenType, float[] newValues) {

		switch (tweenType) {
		case DEFAULT:
			target.setAlpha(newValues[0]);
			break;
		}
	}

}