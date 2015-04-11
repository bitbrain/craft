package de.bitbrain.craft.tweens;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.util.IntegerValueProvider;

public class IntegerValueTween implements TweenAccessor<IntegerValueProvider> {

	public static final int VALUE = 1;

	@Override
	public int getValues(IntegerValueProvider target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case VALUE:
			returnValues[0] = target.getValue();
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void setValues(IntegerValueProvider target, int tweenType, float[] newValues) {

		switch (tweenType) {
		case VALUE:
			target.setValue(Math.round(newValues[0]));
			break;
		}
	}

}