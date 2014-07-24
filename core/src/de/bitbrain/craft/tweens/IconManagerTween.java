package de.bitbrain.craft.tweens;

import aurelienribon.tweenengine.TweenAccessor;
import de.bitbrain.craft.core.IconManager;

public class IconManagerTween implements TweenAccessor<IconManager> {

	public static final int ALPHA = 1;

	@Override
	public int getValues(IconManager target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getAlpha();
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void setValues(IconManager target, int tweenType, float[] newValues) {

		switch (tweenType) {
		case ALPHA:
			target.setAlpha(newValues[0]);
			break;
		}
	}

}