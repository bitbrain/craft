package de.bitbrain.craft.tweens;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteTween implements TweenAccessor<Sprite> {

	public static final int BOUNCE = 1;
	public static final int ALPHA = 2;
	public static final int ROTATION = 3;

	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case BOUNCE:
			returnValues[0] = target.getY();
			return 1;
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case ROTATION:
			returnValues[0] = target.getRotation();
			return 1;
		default:
			return 0;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case BOUNCE:
			target.setY(newValues[0]);
			break;
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g,
					target.getColor().b, newValues[0]);
			break;
		case ROTATION:
			target.setRotation(newValues[0]);
			break;
		}
	}

}
