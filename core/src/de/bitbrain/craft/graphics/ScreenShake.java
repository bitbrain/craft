package de.bitbrain.craft.graphics;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.graphics.Camera;
import com.google.inject.Inject;

import de.bitbrain.craft.animations.CameraTween;
import de.bitbrain.craft.animations.TweenAnimations.TweenType;
import de.bitbrain.craft.inject.StateScoped;

@StateScoped
public class ScreenShake {

  @Inject
  private Camera camera;

  @Inject
  private TweenManager tweenManager;

  static {
    Tween.registerAccessor(Camera.class, new CameraTween());
  }

  public void shake(float strength, final float duration) {
    tweenManager.killTarget(camera);
    final float cameraPosX = camera.position.x;
    final float cameraPosY = camera.position.y;
    Tween.to(camera, TweenType.POS_X.ordinal(), 0.05f).target(cameraPosX + 10f).repeatYoyo(15, 0f)
        .ease(TweenEquations.easeInOutCubic).start(tweenManager);
    Tween.to(camera, TweenType.POS_Y.ordinal(), 0.05f).target(cameraPosY + 10f).repeatYoyo(15, 0f)
    .ease(TweenEquations.easeInOutCubic).start(tweenManager);
  }
}
