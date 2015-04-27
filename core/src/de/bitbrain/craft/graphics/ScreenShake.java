package de.bitbrain.craft.graphics;

import java.security.SecureRandom;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Camera;
import com.google.inject.Inject;

import de.bitbrain.craft.Sizes;
import de.bitbrain.craft.animations.CameraTween;
import de.bitbrain.craft.animations.TweenAnimations.TweenType;
import de.bitbrain.craft.inject.StateScoped;

@StateScoped
public class ScreenShake {
  
  public static final float STEP_INTERVAL = 0.05f;

  @Inject
  private Camera camera;

  @Inject
  private TweenManager tweenManager;
  
  private SecureRandom random = new SecureRandom();

  static {
    Tween.registerAccessor(Camera.class, new CameraTween());
  }

  /**
   * Radial screen shake by using a shrinking circle
   * 
   * @param strength radius of the screen shake
   * @param duration duration (in milliseconds) how long the screen shake should take
   */
  public void shake(float strength, final float duration) {
    final int STEPS = Math.round(duration / STEP_INTERVAL);
    final float STRENGTH_STEP = strength / STEPS;
    tweenManager.killTarget(camera);
    for (int step = 0; step < STEPS; ++step) {
      double angle = Math.toRadians(random.nextFloat() * 360f);
      float x = (float) Math.floor(Sizes.worldWidth() / 2f + strength * Math.cos(angle));
      float y = (float) Math.floor(Sizes.worldHeight() / 2f + strength * Math.sin(angle));
      Tween.to(camera, TweenType.POS_X.ordinal(), STEP_INTERVAL).delay(step * STEP_INTERVAL).target(x).ease(TweenEquations.easeInOutCubic).start(tweenManager);
      Tween.to(camera, TweenType.POS_Y.ordinal(), STEP_INTERVAL).delay(step * STEP_INTERVAL).target(y).ease(TweenEquations.easeInOutCubic).start(tweenManager);
      strength -= STRENGTH_STEP;
    }
  }
}
