package de.bitbrain.craft.animations;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.util.FloatValueProvider;

public class FloatValueTween implements TweenAccessor<FloatValueProvider> {

	public static final int VALUE = 1;

	@Override
	public int getValues(FloatValueProvider target, int tweenType,
			float[] returnValues) {
		switch (tweenType) {
		case VALUE:
			returnValues[0] = target.getValue();
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void setValues(FloatValueProvider target, int tweenType,
			float[] newValues) {

		switch (tweenType) {
		case VALUE:
			target.setValue(newValues[0]);
			break;
		}
	}

}