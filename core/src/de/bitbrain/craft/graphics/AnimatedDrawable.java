package de.bitbrain.craft.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

public class AnimatedDrawable extends BaseDrawable {
  public final Animation anim;

  private float stateTime = 0;

  public AnimatedDrawable(Animation anim) {
    this.anim = anim;
    setMinWidth(anim.getKeyFrame(0).getRegionWidth());
    setMinHeight(anim.getKeyFrame(0).getRegionHeight());
  }

  public void act(float delta) {
    stateTime += delta;
  }

  public void reset() {
    stateTime = 0;
  }

  @Override
  public void draw(Batch batch, float x, float y, float width, float height) {
    batch.draw(anim.getKeyFrame(stateTime), x, y, width, height);
  }
}